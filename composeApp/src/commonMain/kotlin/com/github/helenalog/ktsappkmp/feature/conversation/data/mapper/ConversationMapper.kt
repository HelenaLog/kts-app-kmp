package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.core.utils.DateTimeParser
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ConversationDetail
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.ConversationDto
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.dto.MessageKindDto
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Channel
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.ChannelKind
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Conversation
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import io.github.aakira.napier.Napier
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock


fun ConversationDto.toDomain(dateTimeParser: DateTimeParser): Conversation {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val instant = dateTimeParser.parse(dateUpdated)
    return Conversation(
        id = id,
        isRead = isRead,
        userName = "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}".trim(),
        photoUrl = user.photo?.url,
        channel = Channel(
            id = channel.id,
            name = channel.name,
            kind = channel.kind.toChannelKind(),
            photoUrl = channel.photoUrl
        ),
        lastMessageText = lastMessage?.text.orEmpty(),
        lastMessageKind = lastMessage?.kind?.toDomain(),
        lastMessageAttachmentCount = lastMessage?.attachments?.size ?: 0,
        formattedTime = dateTimeParser.formatConversationTime(instant, today),
        dateUpdated = dateUpdated,
        userId = user.id
    )
}

fun ConversationDto.toDetail() = ConversationDetail(
    userId = user.id,
    userName = listOfNotNull(user.firstName, user.lastName).joinToString(" "),
    photoUrl = user.photo?.url,
    channelId = channel.id,
    botName = channel.name,
    channelKind = channel.kind.toChannelKind(),
    stoppedByManager = state.stoppedByManager,
    channelPhoto = channel.photoUrl
)

private fun MessageKindDto.toDomain() = when (this) {
    MessageKindDto.BOT -> MessageKind.BOT
    MessageKindDto.SERVICE -> MessageKind.SERVICE
    MessageKindDto.MANAGER -> MessageKind.MANAGER
    MessageKindDto.USER -> MessageKind.USER
}

@Suppress("TooGenericExceptionCaught")
private fun formatTime(dateUpdated: String): String = try {
    dateUpdated.substring(11, 16)
} catch (e: IndexOutOfBoundsException) {
    Napier.e("formatTime error: invalid date format")
    ""
}

private fun String.toChannelKind(): ChannelKind = when (this.lowercase()) {
    "tg" -> ChannelKind.TG
    "wz" -> ChannelKind.WZ
    "jv" -> ChannelKind.JV
    else -> ChannelKind.UNKNOWN
}
