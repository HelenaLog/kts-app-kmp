package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageDto
import kotlinx.serialization.Serializable

@Serializable
class WsNewMessagePayloadDto(
    val type: String? = null,
    val data: MessageDto? = null
)
