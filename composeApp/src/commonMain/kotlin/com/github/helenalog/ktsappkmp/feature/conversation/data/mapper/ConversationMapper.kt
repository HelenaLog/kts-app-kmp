package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ConversationDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageKindDto
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Conversation
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import io.github.aakira.napier.Napier


fun ConversationDto.toDomain() = Conversation(
    id = id,
    isRead = isRead,
    userName = "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}".trim(),
    photoUrl = user.photo?.url,
    channelKind = channel.kind.toChannelKind(),
    lastMessageText = lastMessage?.text.orEmpty(),
    lastMessageKind = lastMessage?.kind?.toDomain(),
    formattedTime = formatTime(dateUpdated),
    dateUpdated = dateUpdated,
    userId = user.id
)

private fun MessageKindDto.toDomain() = when (this) {
    MessageKindDto.BOT -> MessageKind.BOT
    MessageKindDto.SERVICE -> MessageKind.SERVICE
    MessageKindDto.MANAGER -> MessageKind.MANAGER
    MessageKindDto.USER -> MessageKind.USER
}

private fun formatTime(dateUpdated: String): String = try {
    dateUpdated.substring(11, 16)
} catch (e: Exception) {
    Napier.e("formatTime error: invalid date format")
    ""
}

private fun String.toChannelKind(): ChannelKind = when (this.lowercase()) {
    "tg" -> ChannelKind.TG
    "wz" -> ChannelKind.WZ
    "jv" -> ChannelKind.JV
    else -> ChannelKind.UNKNOWN
}
