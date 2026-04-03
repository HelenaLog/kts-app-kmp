package com.github.helenalog.ktsappkmp.feature.chat.domain.repository

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage

interface ChatRepository {
    suspend fun getMessages(
        conversationId: Long,
        userId: String,
        channelId: String,
        limit: Int = DEFAULT_LIMIT,
        fromId: String? = null,
    ): Result<List<ChatMessage>>

    suspend fun sendMessage(
        conversationId: Long,
        text: String?,
        attachments: List<ChatAttachment>
    ): Result<Unit>

    suspend fun uploadAttachment(
        fileName: String,
        bytes: ByteArray,
        mimeType: String,
    ): Result<ChatAttachment>

    companion object {
        const val DEFAULT_LIMIT = 20
    }
}