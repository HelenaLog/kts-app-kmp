package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository

class GetMessagesUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(
        conversationId: Long,
        userId: String,
        channelId: String,
        fromId: String? = null,
    ): Result<List<ChatMessage>> =
        repository.getMessages(conversationId, userId, channelId, fromId = fromId)
}