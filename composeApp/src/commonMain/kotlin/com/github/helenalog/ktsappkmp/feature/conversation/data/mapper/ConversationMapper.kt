package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ConversationDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageKindDto
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Conversation
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind

fun ConversationDto.toDomain() = Conversation(
    id = id,
    isRead = isRead,
    userName = "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}".trim(),
    photoUrl = user.photo?.url,
    channelKind = channel.kind.toChannelKind(),
    lastMessageText = lastMessage?.text.orEmpty(),
    lastMessageKind = lastMessage?.kind?.toDomain(),
    dateUpdated = dateUpdated
)

private fun MessageKindDto.toDomain(): MessageKind = when (this) {
    MessageKindDto.BOT -> MessageKind.BOT
    MessageKindDto.SERVICE -> MessageKind.SERVICE
    MessageKindDto.MANAGER -> MessageKind.MANAGER
    MessageKindDto.USER -> MessageKind.USER
}

private fun String.toChannelKind(): ChannelKind = when (lowercase()) {
    "tg" -> ChannelKind.TG
    "wz" -> ChannelKind.WZ
    "jv" -> ChannelKind.JV
    else -> ChannelKind.UNKNOWN
}