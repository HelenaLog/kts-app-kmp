package com.github.helenalog.ktsappkmp.feature.chat.data.remote.request

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.SendMessageAttachmentDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequestDto(
    @SerialName("conversation_id")
    val conversationId: Long,
    @SerialName("message_text")
    val messageText: String? = null,
    @SerialName("attachments")
    val attachments: List<SendMessageAttachmentDto> = emptyList()
)
