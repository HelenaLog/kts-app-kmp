package com.github.helenalog.ktsappkmp.core.data.remote.dto.response

import com.github.helenalog.ktsappkmp.core.data.remote.dto.ConversationDto
import kotlinx.serialization.Serializable

@Serializable
data class ConversationsResponse(
    val conversations: List<ConversationDto>
)