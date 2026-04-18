package com.github.helenalog.ktsappkmp.feature.conversation.data.repository

import com.github.helenalog.ktsappkmp.core.data.remote.network.asApiException
import com.github.helenalog.ktsappkmp.core.utils.DateTimeParser
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.api.ConversationApi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationFilter
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ConversationsPage
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationRepository
import kotlin.coroutines.cancellation.CancellationException

class ConversationRepositoryImpl(
    private val api: ConversationApi,
    private val conversationDao: ConversationDao,
    private val dateTimeParser: DateTimeParser
) : ConversationRepository {

    override suspend fun getConversations(
        query: String,
        limit: Int,
        offset: Int,
        filter: ConversationFilter,
        isRead: Boolean?,
    ): Result<ConversationsPage> {
        return suspendRunCatching { fetchRemotePage(query, limit, offset, filter, isRead) }
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { fetchCachedPage(query, limit, offset, filter, isRead, it) }
            )
    }

    private suspend fun fetchRemotePage(
        query: String,
        limit: Int,
        offset: Int,
        filter: ConversationFilter,
        isRead: Boolean?,
    ): ConversationsPage {
        val channelIds = filter.selectedChannelIds.takeIf { it.isNotEmpty() }?.toList()

        val response = api.getConversations(
            limit = limit,
            offset = offset,
            query = query.takeIf { it.isNotBlank() }.orEmpty(),
            channelIds = channelIds,
            listId = filter.selectedListId,
            isRead = isRead,
        )
        val conversations = response.data.conversations.orEmpty()
            .map { it.toDomain(dateTimeParser) }
            .let { list ->
                if (filter.selectedChannelKinds.isNotEmpty() && filter.selectedChannelIds.isEmpty()) {
                    list.filter { it.channel.kind in filter.selectedChannelKinds }
                } else list
            }

        if (offset == 0) {
            conversationDao.deleteAll()
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
        filter: ConversationFilter,
        isRead: Boolean?,
        e: Throwable
    ): Result<ConversationsPage> {
        if (e is CancellationException) throw e
        val cached = conversationDao.getByQuery(query)
            .let { list ->
                if (isRead == null) list else list.filter { it.isRead == isRead }
            }
            .let { list ->
                if (filter.selectedChannelKinds.isNotEmpty() && filter.selectedChannelIds.isEmpty()) {
                    list.filter { ChannelKind.valueOf(it.channelKind) in filter.selectedChannelKinds }
                } else list
            }
        if (cached.isEmpty()) return Result.failure(e.asApiException())
        val pagedCached = cached.drop(offset).take(limit)
        return Result.success(
            ConversationsPage(
                conversations = pagedCached.map { it.toDomain() },
                hasMore = cached.size > offset + limit
            )
        )
    }
}
