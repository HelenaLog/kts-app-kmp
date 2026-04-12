package com.github.helenalog.ktsappkmp.feature.chat.data.repository

import com.github.helenalog.ktsappkmp.core.data.remote.network.CentrifugeSession
import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkConfig
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.chat.data.mapper.WsMessageMapper
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatWebSocketApi
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.WsNewMessagePayloadDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatWebSocketRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.WebSocketEvent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.DefaultWebSocketSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class ChatWebSocketRepositoryImpl(
    private val wsClient: HttpClient,
    private val restApi: ChatWebSocketApi,
    private val networkConfig: NetworkConfig,
    private val mapper: WsMessageMapper,
    private val json: Json
) : ChatWebSocketRepository {

    override fun observeMessages(
        conversationId: Long,
        maxRetries: Int,
    ): Flow<WebSocketEvent> = channelFlow {
        var attempt = 0
        while (currentCoroutineContext().isActive) {
            suspendRunCatching { connectAndListen { send(it) } }
                .onSuccess { attempt = 0 }
                .onFailure { error ->
                    attempt = handleError(
                        error = error,
                        attempt = attempt,
                        maxRetries = maxRetries
                    ) { event -> send(event) } ?: return@channelFlow
                }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun connectAndListen(emit: suspend (WebSocketEvent) -> Unit) {
        val (authToken, subToken) = obtainTokens()
        val channel = extractChannelFromJwt(subToken) ?: error(ERROR_EXTRACT_CHANNEL)

        wsClient.webSocket(urlString = networkConfig.wsUrl) {
            val centrifuge = CentrifugeSession(this, json)
            centrifuge.connect(authToken)
            emit(WebSocketEvent.Connected)
            centrifuge.subscribe(channel, subToken)
            listenMessages(centrifuge, emit)
        }
    }

    private suspend fun obtainTokens(): Pair<String, String> {
        val authToken = restApi.obtainWsAuthToken().data.authToken
        val subToken = restApi.obtainSubscriptionToken().data.subscriptionToken
        return authToken to subToken
    }

    private suspend fun DefaultWebSocketSession.listenMessages(
        centrifuge: CentrifugeSession,
        emit: suspend (WebSocketEvent) -> Unit,
    ) {
        while (isActive) {
            val reply = centrifuge.receiveReply() ?: break
            val data = reply.push?.pub?.data
            if (data != null) {
                val payload = json.decodeFromJsonElement<WsNewMessagePayloadDto>(data)
                mapper.map(payload)?.let { emit(WebSocketEvent.NewMessage(it)) }
            }
        }
    }

    private suspend fun handleError(
        error: Throwable,
        attempt: Int,
        maxRetries: Int,
        emit: suspend (WebSocketEvent) -> Unit,
    ): Int? {
        val next = attempt + 1
        if (next >= maxRetries) {
            emit(WebSocketEvent.Error(error))
            return null
        }
        emit(WebSocketEvent.Reconnecting(next))
        delay(RECONNECT_BASE_MS * (1L shl minOf(next - 1, 4)))
        return next
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun extractChannelFromJwt(token: String): String? = runCatching {
        val payloadB64 = token.split(".").getOrNull(1) ?: return@runCatching null
        val decoded = Base64.UrlSafe.decode(payloadB64).decodeToString()
        json.parseToJsonElement(decoded).jsonObject["channel"]?.jsonPrimitive?.content
    }.getOrNull()

    companion object {
        private const val RECONNECT_BASE_MS = 1_000L
        private const val ERROR_EXTRACT_CHANNEL = "Не удалось извлечь канал из subscription токена"

    }
}
