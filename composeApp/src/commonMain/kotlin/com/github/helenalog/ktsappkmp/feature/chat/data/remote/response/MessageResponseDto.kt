package com.github.helenalog.ktsappkmp.feature.chat.data.remote.response

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponseDto(
    @SerialName("messages")
    val messages: List<MessageDto>
)
