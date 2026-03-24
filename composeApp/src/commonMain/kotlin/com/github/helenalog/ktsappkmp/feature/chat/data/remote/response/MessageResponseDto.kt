package com.github.helenalog.ktsappkmp.feature.chat.data.remote.response

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageDto
import kotlinx.serialization.Serializable

@Serializable
data class MessagesResponseDto(
    val messages: List<MessageDto>
)