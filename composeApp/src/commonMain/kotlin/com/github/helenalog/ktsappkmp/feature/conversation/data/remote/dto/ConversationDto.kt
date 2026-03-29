package com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationDto(
    @SerialName("id")
    val id: Long,
    @SerialName("is_read")
    val isRead: Boolean,
    @SerialName("date_updated")
    val dateUpdated: String,
    @SerialName("user")
    val user: UserDto,
    @SerialName("channel")
    val channel: ChannelDto,
    @SerialName("state")
    val state: ChatStateDto,
    @SerialName("last_message")
    val lastMessage: MessageDto? = null
)
