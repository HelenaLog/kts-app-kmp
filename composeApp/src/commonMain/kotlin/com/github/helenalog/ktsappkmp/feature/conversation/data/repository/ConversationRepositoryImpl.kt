package com.github.helenalog.ktsappkmp.feature.conversation.data.repository

import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.api.ConversationApi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository
import kotlin.coroutines.cancellation.CancellationException


class ConversationRepositoryImpl(
    private val api: ConversationApi,
    private val conversationDao: ConversationDao,
) : ConversationRepository {

    override suspend fun getConversations(
        query: String,
        limit: Int,
        offset: Int,
        filter: ConversationFilter
    ): Result<ConversationsPage> {
        return suspendRunCatching { fetchRemotePage(query, limit, offset, filter) }
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { fetchCachedPage(query, limit, offset, it) }
            )
    }

    private suspend fun fetchRemotePage(
        query: String,
        limit: Int,
        offset: Int,
        filter: ConversationFilter
    ): ConversationsPage {
        val channelIds = filter.selectedChannelIds.takeIf { it.isNotEmpty() }?.toList()

        val response = api.getConversations(
            limit = limit,
            offset = offset,
            query = query.takeIf { it.isNotBlank() }.orEmpty(),
            channelIds = channelIds,
            listId = filter.selectedListId
        )
        val conversations = response.data.conversations.orEmpty()
            .map { it.toDomain() }
            .let { list ->
                if (filter.selectedChannelKinds.isNotEmpty() && filter.selectedChannelIds.isEmpty()) {
                    list.filter { it.channel.kind in filter.selectedChannelKinds }
                } else list
            }

        if (offset == 0) {
            conversationDao.upsertAll(conversations.map { it.toEntity() })
        }
        return ConversationsPage(
            conversations = conversations,
            hasMore = conversations.size >= limit
        )
    }

    private suspend fun fetchCachedPage(
        query: String,
        limit: Int,
        offset: Int,
        e: Throwable
    ): Result<ConversationsPage> {
        if (e is CancellationException) throw e
        val cached = conversationDao.getByQuery(query)
        if (cached.isEmpty()) return Result.failure(e)
        val pagedCached = cached.drop(offset).take(limit)
        return Result.success(
            ConversationsPage(
                conversations = pagedCached.map { it.toDomain() },
                hasMore = cached.size > offset + limit
            )
        )
    }
}
