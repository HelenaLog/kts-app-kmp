package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.data.remote.api.SmartbotApi
import com.github.helenalog.ktsappkmp.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.domain.repository.ConversationRepository
import io.github.aakira.napier.Napier
import kotlin.coroutines.cancellation.CancellationException

class ConversationRepositoryImpl : ConversationRepository {
    private val api = SmartbotApi(Networking.httpClient)

    override suspend fun getConversations(
        query: String,
        limit: Int,
        offset: Int
    ): Result<ConversationsPage> = runCatching {
        val response = api.getConversations(
            limit = limit,
            offset = offset,
            query = query.takeIf { it.isNotBlank() }.orEmpty()
        )
        ConversationsPage(
            conversations = response.data.conversations.map { it.toDomain() },
            hasMore = response.data.conversations.size >= limit
        )
    }.onFailure { e ->
        if (e is CancellationException) throw e
    }
}