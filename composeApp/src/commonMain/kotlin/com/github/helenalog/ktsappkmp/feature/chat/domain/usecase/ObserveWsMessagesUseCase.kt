package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatWebSocketRepository
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.WebSocketEvent
import kotlinx.coroutines.flow.Flow

class ObserveWsMessagesUseCase(
    private val repository: ChatWebSocketRepository
) {
    operator fun invoke(conversationId: Long): Flow<WebSocketEvent> =
        repository.observeMessages(conversationId, MAX_RETRIES)

    private companion object {
        const val MAX_RETRIES = 5
    }
}
