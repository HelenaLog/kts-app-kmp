package com.github.helenalog.ktsappkmp.data.remote.dto

import com.github.helenalog.ktsappkmp.data.remote.dto.ChannelKindDto
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
) {
    val channelKind: ChannelKindDto
        get() = ChannelKindDto.Companion.fromString(channel.kind)

    val formattedTime: String
        get() = try {
            dateUpdated.substring(11, 16)
        } catch (e: Exception) {
            ""
        }
}