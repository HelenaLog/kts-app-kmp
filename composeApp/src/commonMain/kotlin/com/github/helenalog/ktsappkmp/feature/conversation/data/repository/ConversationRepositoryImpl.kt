package com.github.helenalog.ktsappkmp.feature.conversation.data.repository

import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.conversation.data.api.ConversationsApi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository
import kotlin.coroutines.cancellation.CancellationException


class ConversationRepositoryImpl(
    private val api: ConversationsApi,
    private val conversationDao: ConversationDao,
) : ConversationRepository {

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
        val conversations = response.data.conversations.orEmpty().map { it.toDomain() }
        if (offset == 0) { conversationDao.upsertAll(conversations.map { it.toEntity() }) }
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
        val cached = conversationDao.getByQuery(query)
        if (cached.isEmpty()) throw e
        val pagedCached = cached.drop(offset).take(limit)
        return ConversationsPage(
            conversations = pagedCached.map { it.toDomain() },
            hasMore = cached.size > offset + limit
        )
    }
}
