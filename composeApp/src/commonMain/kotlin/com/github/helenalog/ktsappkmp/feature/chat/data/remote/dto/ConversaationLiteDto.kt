package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ChannelDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ChatStateDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class ConversationLiteDto(
    val channel: ChannelDto,
    val state: ChatStateDto,
    val user: UserDto,
)