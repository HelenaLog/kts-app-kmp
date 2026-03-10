package com.github.helenalog.ktsappkmp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationDto(
    val id: Long,
    @SerialName("is_read")
    val isRead: Boolean,
    @SerialName("date_updated")
    val dateUpdated: String,
    val user: UserDto,
    val channel: ChannelDto,
    val state: StateDto,
    @SerialName("last_message")
    val lastMessage: MessageDto? = null
)