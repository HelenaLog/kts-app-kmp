package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.data.local.DatabaseProvider
import com.github.helenalog.ktsappkmp.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.data.remote.api.SmartbotApi
import com.github.helenalog.ktsappkmp.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.domain.repository.ConversationRepository
import com.github.helenalog.ktsappkmp.utils.suspendRunCatching
import kotlin.coroutines.cancellation.CancellationException

class ConversationRepositoryImpl : ConversationRepository {
    private val api = SmartbotApi(Networking.httpClient)
    private val dao = DatabaseProvider.instance.conversationDao()

    override suspend fun getConversations(
        query: String,
        limit: Int,
        offset: Int
    ): Result<ConversationsPage> = suspendRunCatching {
        try {
            fetchRemotePage(query, limit, offset)
        } catch (e: Exception) {
            fetchCachedPage(query, limit, offset, e)
        }
    }

    private suspend fun fetchRemotePage(
        query: String,
        limit: Int,
        offset: Int
    ): ConversationsPage {
        val response = api.getConversations(
            limit = limit,
            offset = offset,
            query = query.takeIf { it.isNotBlank() }.orEmpty()
        )
        val conversations = response.data.conversations.map { it.toDomain() }
        if (offset == 0) { dao.upsertAll(conversations.map { it.toEntity() }) }
        return ConversationsPage(
            conversations = conversations,
            hasMore = conversations.size >= limit
        )
    }

    private suspend fun fetchCachedPage(
        query: String,
        limit: Int,
        offset: Int,
        e: Exception
    ): ConversationsPage {
        if (e is CancellationException) throw e
        val cached = dao.getByQuery(query)
        if (cached.isEmpty()) throw e
        val pagedCached = cached.drop(offset).take(limit)
        return ConversationsPage(
            conversations = pagedCached.map { it.toDomain() },
            hasMore = cached.size > offset + limit
        )
    }
}