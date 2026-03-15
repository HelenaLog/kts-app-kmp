package com.github.helenalog.ktsappkmp.data.remote.dto

import com.github.helenalog.ktsappkmp.data.remote.dto.MessageKindDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val text: String? = null,
    val kind: MessageKindDto,
    @SerialName("date_created")
    val dateCreated: String? = null,
    @SerialName("conversation_id")
    val conversationId: Long? = null,
)