package com.github.helenalog.ktsappkmp.feature.chat.domain.repository

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatWebSocketRepository {
    fun observeMessages(
        conversationId: Long,
        maxRetries: Int = DEFAULT_MAX_RETRIES,
    ): Flow<WebSocketEvent>

    companion object {
        const val DEFAULT_MAX_RETRIES = 5
    }
}

sealed interface WebSocketEvent {
    data class NewMessage(val message: ChatMessage) : WebSocketEvent
    data object Connected : WebSocketEvent
    data class Reconnecting(val attempt: Int) : WebSocketEvent
    data class Error(val cause: Throwable) : WebSocketEvent
}