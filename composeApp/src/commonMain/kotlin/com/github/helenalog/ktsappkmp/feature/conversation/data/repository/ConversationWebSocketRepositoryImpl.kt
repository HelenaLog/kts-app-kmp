package com.github.helenalog.ktsappkmp.feature.conversation.data.repository

import com.github.helenalog.ktsappkmp.core.data.remote.network.CentrifugeSession
import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkConfig
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatWebSocketApi
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.WsNewMessagePayloadDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.ConversationWsMessageMapper
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWebSocketRepository
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWsEvent
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

class ConversationWebSocketRepositoryImpl(
    private val wsClient: HttpClient,
    private val wsApi: ChatWebSocketApi,
    private val networkConfig: NetworkConfig,
    private val mapper: ConversationWsMessageMapper,
    private val json: Json
) : ConversationWebSocketRepository {

    override fun observeUpdates(maxRetries: Int): Flow<ConversationWsEvent> = channelFlow {
        var attempt = 0
        while (currentCoroutineContext().isActive) {
            suspendRunCatching { connectAndListen { send(it) } }
                .onSuccess { attempt = 0 }
                .onFailure { error ->
                    attempt = handleError(error, attempt, maxRetries) { send(it) }
                        ?: return@channelFlow
                }
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun connectAndListen(emit: suspend (ConversationWsEvent) -> Unit) {
        val (authToken, subToken) = obtainTokens()
        val channel = extractChannelFromJwt(subToken) ?: error(ERROR_EXTRACT_CHANNEL)

        wsClient.webSocket(urlString = networkConfig.wsUrl) {
            val centrifuge = CentrifugeSession(this, json)
            centrifuge.connect(authToken)
            emit(ConversationWsEvent.Connected)
            centrifuge.subscribe(channel, subToken)
            listenMessages(centrifuge, emit)
        }
    }

    private suspend fun obtainTokens(): Pair<String, String> {
        val authToken = wsApi.obtainWsAuthToken().data.authToken
        val subToken = wsApi.obtainSubscriptionToken().data.subscriptionToken
        return authToken to subToken
    }

    private fun extractChannelFromJwt(token: String): String? = runCatching {
        val payloadB64 = token.split(".").getOrNull(1) ?: return@runCatching null
        val decoded = Base64.UrlSafe.decode(payloadB64).decodeToString()
        json.parseToJsonElement(decoded).jsonObject["channel"]?.jsonPrimitive?.content
    }.getOrNull()

    private suspend fun DefaultWebSocketSession.listenMessages(
        centrifuge: CentrifugeSession,
        emit: suspend (ConversationWsEvent) -> Unit,
    ) {
        while (isActive) {
            val reply = centrifuge.receiveReply() ?: break
            val data = reply.push?.pub?.data ?: continue
            val payload = json.decodeFromJsonElement<WsNewMessagePayloadDto>(data)
            mapper.map(payload)?.let { emit(it) }
        }
    }

    private suspend fun handleError(
        error: Throwable,
        attempt: Int,
        maxRetries: Int,
        emit: suspend (ConversationWsEvent) -> Unit,
    ): Int? {
        val next = attempt + 1
        if (next >= maxRetries) {
            emit(ConversationWsEvent.Error(error))
            return null
        }
        emit(ConversationWsEvent.Reconnecting(next))
        delay(RECONNECT_BASE_MS * (1L shl minOf(next - 1, 4)))
        return next
    }

    companion object {
        private const val ERROR_EXTRACT_CHANNEL = "Не удалось извлечь канал из subscription токена"
        private const val RECONNECT_BASE_MS = 1_000L
    }
}
