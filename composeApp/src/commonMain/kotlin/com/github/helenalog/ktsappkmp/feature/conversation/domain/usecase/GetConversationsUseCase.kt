package com.github.helenalog.ktsappkmp.feature.conversation.domain.usecase

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository

class GetConversationsUseCase(
    private val repository: ConversationRepository
) {
    suspend operator fun invoke(
        query: String = "",
        limit: Int = 20,
        offset: Int = 0,
        filter: ConversationFilter = ConversationFilter(),
        isRead: Boolean? = null
    ): Result<ConversationsPage> = repository.getConversations(query, limit, offset, filter, isRead)
}
