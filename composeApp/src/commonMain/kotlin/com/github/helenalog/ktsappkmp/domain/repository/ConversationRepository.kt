package com.github.helenalog.ktsappkmp.domain.repository

import com.github.helenalog.ktsappkmp.domain.model.Conversation
import com.github.helenalog.ktsappkmp.domain.model.ConversationsPage
import kotlinx.coroutines.flow.Flow

interface ConversationRepository {
    suspend fun getConversations(
        query: String = "",
        limit: Int = 20,
        offset: Int = 0
    ): Result<ConversationsPage>
}