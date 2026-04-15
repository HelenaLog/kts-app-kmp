package com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase

import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWebSocketRepository
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWsEvent
import kotlinx.coroutines.flow.Flow

class ObserveConversationUpdatesUseCase(
    private val repository: ConversationWebSocketRepository
) {
    operator fun invoke(): Flow<ConversationWsEvent> =
        repository.observeUpdates()
}
