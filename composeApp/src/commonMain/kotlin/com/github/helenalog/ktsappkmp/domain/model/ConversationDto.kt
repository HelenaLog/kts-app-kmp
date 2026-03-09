package com.github.helenalog.ktsappkmp.domain.model

data class ConversationDto(
    val id: Long,
    val isRead: Boolean,
    val dateUpdated: String,
    val user: UserDto,
    val channel: ChannelDto,
    val state: StateDto,
    val lastMessage: MessageDto? = null
) {
    val channelKind: ChannelKind
        get() = ChannelKind.fromString(channel.kind)

    val formattedTime: String
        get() = try {
            dateUpdated.substring(11, 16)
        } catch (e: Exception) {
            ""
        }
}