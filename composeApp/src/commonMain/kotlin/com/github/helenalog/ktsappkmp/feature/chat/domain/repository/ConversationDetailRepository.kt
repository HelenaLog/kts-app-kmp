package com.github.helenalog.ktsappkmp.feature.chat.domain.repository

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ConversationDetail

interface ConversationDetailRepository {

    suspend fun getDetail(
        conversationId: Long,
        userId: String,
    ): Result<ConversationDetail>
}
