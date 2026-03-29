package com.github.helenalog.ktsappkmp.core.data.remote.network

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.CentrifugeCommandDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.CentrifugeReplyDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.ConnectRequestDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.SubscribeRequestDto
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

internal class CentrifugeSession(
    private val session: DefaultWebSocketSession,
    private val json: Json
) {
    private var commandId = 0

    suspend fun connect(token: String) {
        sendConnect(token)
        val reply = receiveReply() ?: throw IllegalStateException(DISCONNECTED)
        if (reply.error != null) error("$CONNECT ${reply.error.code}")
    }

    suspend fun subscribe(channel: String, token: String) {
        sendSubscribe(channel, token)
        val reply = receiveReply() ?: throw IllegalStateException(SUBSCRIBE_FAILED)
        if (reply.error != null) error("$SUBSCRIBE ${reply.error.code}")
    }

    @Suppress("ReturnCount")
    suspend fun receiveReply(): CentrifugeReplyDto? {
        for (frame in session.incoming) {
            when (frame) {
                is Frame.Text -> {
                    val text = frame.readText()
                    if (text == EMPTY_OBJECT) {
                        session.send(Frame.Text(EMPTY_OBJECT))
                        continue
                    }
                    return json.decodeFromString<CentrifugeReplyDto>(text)
                }

                is Frame.Close -> return null
                else -> Unit
            }
        }
        return null
    }

    private suspend fun sendConnect(token: String) {
        send(
            CentrifugeCommandDto(
                id = nextId(),
                connect = ConnectRequestDto(token = token)
            )
        )
    }

    private suspend fun sendSubscribe(channel: String, token: String) {
        send(
            CentrifugeCommandDto(
                id = nextId(),
                subscribe = SubscribeRequestDto(channel = channel, token = token)
            )
        )
    }

    private suspend fun send(command: CentrifugeCommandDto) {
        session.send(Frame.Text(json.encodeToString(command)))
        session.flush()
    }

    private fun nextId() = ++commandId

    companion object {
        private const val DISCONNECTED = "Соединение разорвано"
        private const val SUBSCRIBE_FAILED = "Не удалось подписаться"
        private const val CONNECT = "Ошибка подключения"
        private const val SUBSCRIBE = "Ошибка подписки"
        private const val EMPTY_OBJECT = "{}"
    }
}
