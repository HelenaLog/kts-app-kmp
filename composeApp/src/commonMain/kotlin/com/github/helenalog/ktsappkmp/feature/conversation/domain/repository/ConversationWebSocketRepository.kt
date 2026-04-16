package com.github.helenalog.ktsappkmp.feature.conversation.domain.repository

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import kotlinx.coroutines.flow.Flow

interface ConversationWebSocketRepository {
    fun observeUpdates(maxRetries: Int = DEFAULT_MAX_RETRIES): Flow<ConversationWsEvent>

    companion object {
        const val DEFAULT_MAX_RETRIES = 5
    }
}

sealed interface ConversationWsEvent {
    data object Connected : ConversationWsEvent
    data class NewMessage(
        val conversationId: Long,
        val lastMessageText: String,
        val lastMessageKind: MessageKind?,
        val lastMessageAttachmentCount: Int,
        val formattedTime: String,
        val dateUpdated: String,
        val isRead: Boolean
    ) : ConversationWsEvent

    data class Reconnecting(val attempt: Int) : ConversationWsEvent
    data class Error(val cause: Throwable) : ConversationWsEvent
}
