package com.github.helenalog.ktsappkmp.feature.chat.domain.repository

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage

interface ChatRepository {
    suspend fun getMessages(
        conversationId: Long,
        userId: String,
        channelId: String,
        limit: Int = DEFAULT_LIMIT,
        fromId: String? = null,
    ): Result<List<ChatMessage>>

    companion object {
        const val DEFAULT_LIMIT = 20
    }
}