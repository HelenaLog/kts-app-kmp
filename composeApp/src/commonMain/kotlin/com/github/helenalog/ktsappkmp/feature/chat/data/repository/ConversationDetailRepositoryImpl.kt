package com.github.helenalog.ktsappkmp.feature.chat.data.repository

import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatApi
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ConversationDetail
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ConversationDetailRepository
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toDetail

class ConversationDetailRepositoryImpl(
    private val api: ChatApi,
) : ConversationDetailRepository {

    override suspend fun getDetail(
        conversationId: Long,
        userId: String,
    ): Result<ConversationDetail> = suspendRunCatching {
        api.getConversation(conversationId, userId).data.toDetail()
    }
}
