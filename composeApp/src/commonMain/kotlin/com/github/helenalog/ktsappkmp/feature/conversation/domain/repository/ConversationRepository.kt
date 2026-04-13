package com.github.helenalog.ktsappkmp.feature.conversation.domain.repository

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage

interface ConversationRepository {
    suspend fun getConversations(
        query: String = "",
        limit: Int = 20,
        offset: Int = 0,
        filter: ConversationFilter = ConversationFilter(),
        isRead: Boolean? = null
    ): Result<ConversationsPage>
}
