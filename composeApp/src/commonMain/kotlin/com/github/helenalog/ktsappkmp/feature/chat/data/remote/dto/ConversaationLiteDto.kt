package com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ChannelDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ChatStateDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationLiteDto(
    @SerialName("channel")
    val channel: ChannelDto,
    @SerialName("state")
    val state: ChatStateDto,
    @SerialName("user")
    val user: UserDto
)