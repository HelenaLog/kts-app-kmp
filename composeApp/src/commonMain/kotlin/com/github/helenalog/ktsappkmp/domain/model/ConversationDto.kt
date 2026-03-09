package com.github.helenalog.ktsappkmp.domain.model

data class ConversationDto(
    val id: Long,
    val isRead: Boolean,
    val dateUpdated: String,
    val user: UserDto,
    val channel: ChannelDto,
    val state: StateDto,
    val lastMessage: MessageDto? = null
)