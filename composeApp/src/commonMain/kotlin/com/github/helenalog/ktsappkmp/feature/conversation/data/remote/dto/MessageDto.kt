package com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto

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
)