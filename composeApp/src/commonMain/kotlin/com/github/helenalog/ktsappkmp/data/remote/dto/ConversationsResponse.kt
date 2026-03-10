package com.github.helenalog.ktsappkmp.data.remote.dto

import com.github.helenalog.ktsappkmp.data.remote.dto.ConversationDto
import kotlinx.serialization.Serializable

@Serializable
data class ConversationsResponse(
    val conversations: List<ConversationDto>
)