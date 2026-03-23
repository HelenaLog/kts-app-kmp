package com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository

class GetConversationsUseCase(
    private val repository: ConversationRepository
) {
    suspend operator fun invoke(
        query: String = "",
        limit: Int = 20,
        offset: Int = 0
    ): Result<ConversationsPage> = repository.getConversations(query, limit, offset)
}