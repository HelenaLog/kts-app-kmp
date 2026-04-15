package com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto

import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.AttachmentDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("id")
    val id: String,
    @SerialName("text")
    val text: String? = null,
    @SerialName("kind")
    val kind: MessageKindDto,
    @SerialName("date_created")
    val dateCreated: String? = null,
    @SerialName("conversation_id")
    val conversationId: Long? = null,
    @SerialName("attachments")
    val attachments: List<AttachmentDto> = emptyList(),
    @SerialName("manager_email")
    val managerEmail: String? = null,
)
