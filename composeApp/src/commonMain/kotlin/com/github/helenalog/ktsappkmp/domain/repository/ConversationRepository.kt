package com.github.helenalog.ktsappkmp.domain.repository

import com.github.helenalog.ktsappkmp.domain.model.ConversationsPage

interface ConversationRepository {
    suspend fun getConversations(
        query: String = "",
        limit: Int = 20,
        offset: Int = 0
    ): Result<ConversationsPage>
}