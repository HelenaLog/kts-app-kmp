package com.github.helenalog.ktsappkmp.feature.conversation.data.remote.response

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ConversationDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationsResponse(
    @SerialName("conversations")
    val conversations: List<ConversationDto>
)