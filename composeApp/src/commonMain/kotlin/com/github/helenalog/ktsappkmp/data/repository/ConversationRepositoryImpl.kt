package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.data.remote.api.SmartbotApi
import com.github.helenalog.ktsappkmp.data.remote.dto.ConversationsPage
import com.github.helenalog.ktsappkmp.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.domain.repository.ConversationRepository
import io.github.aakira.napier.Napier

class ConversationRepositoryImpl : ConversationRepository {
    private val api = SmartbotApi(Networking.httpClient)

    override suspend fun getConversations(
        query: String,
        limit: Int,
        offset: Int
    ): Result<ConversationsPage> = runCatching {
        Napier.d("getConversations called")
        val response = api.getConversations(
            limit = limit,
            offset = offset,
            query = query.takeIf { it.isNotBlank() }.orEmpty()
        )
        Napier.d("getConversations response: $response")
        ConversationsPage(
            conversations = response.data.conversations,
            hasMore = response.data.conversations.size >= limit
        )
    }
}