package com.github.helenalog.ktsappkmp.feature.chat.data.repository

import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.ChatApi
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.SendMessageAttachmentDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.SendMessageRequestDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachmentType
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.ChatRepository
import toDomain


class ChatRepositoryImpl(
    private val api: ChatApi,
) : ChatRepository {

    override suspend fun getMessages(
        conversationId: Long,
        userId: String,
        channelId: String,
        limit: Int,
        fromId: String?,
    ): Result<List<ChatMessage>> = suspendRunCatching {
        api.getMessages(conversationId, userId, channelId, limit, fromId)
            .data.messages.map { it.toDomain() }
    }

    override suspend fun sendMessage(
        conversationId: Long,
        text: String?,
        attachments: List<ChatAttachment>
    ): Result<Unit> = suspendRunCatching {
        api.sendMessage(
            SendMessageRequestDto(
                conversationId = conversationId,
                messageText = text,
                attachments = attachments.map { it.toDto() },
            )
        )
        Unit
    }

    override suspend fun uploadAttachment(
        fileName: String,
        bytes: ByteArray,
        mimeType: String
    ): Result<ChatAttachment> = suspendRunCatching {
        api.uploadAttachment(fileName, bytes).data.toDomain()
    }

    private fun ChatAttachment.toDto() = SendMessageAttachmentDto(
        id = id,
        asDocument = type != ChatAttachmentType.IMAGE,
    )

    private companion object {
        const val IMAGE_TYPE = "image"
    }
}