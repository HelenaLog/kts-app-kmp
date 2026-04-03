package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ConversationDetail
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ConversationDetailRepository

class GetConversationDetailUseCase(
    private val repository: ConversationDetailRepository
) {
    suspend operator fun invoke(conversationId: Long, userId: String): Result<ConversationDetail> =
        repository.getDetail(conversationId, userId)
}