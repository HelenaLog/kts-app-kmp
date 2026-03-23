package com.github.helenalog.ktsappkmp.feature.conversation.data.remote.response

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ConversationDto
import kotlinx.serialization.Serializable

@Serializable
data class ConversationsResponse(
    val conversations: List<ConversationDto>
)