package com.github.helenalog.ktsappkmp.feature.chat.data.repository

import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.chat.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatApi
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ConversationDetail
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ConversationDetailRepository

class ConversationDetailRepositoryImpl(
    private val api: ChatApi,
) : ConversationDetailRepository {

    override suspend fun getDetail(
        conversationId: Long,
        userId: String,
    ): Result<ConversationDetail> = suspendRunCatching {
        api.getConversationLite(conversationId, userId).data.toDomain()
    }
}
