package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.core.utils.DateTimeParser
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.WsNewMessagePayloadDto
import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWsEvent
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

class ConversationWsMessageMapper(
    private val dateTimeParser: DateTimeParser
) {
    fun map(payload: WsNewMessagePayloadDto): ConversationWsEvent.NewMessage? {
        val messageDto = payload.data ?: return null
        val conversationId = messageDto.conversationId ?: return null
        val dateCreated = messageDto.dateCreated.orEmpty()
        val formattedTime = runCatching {
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
            dateTimeParser.formatConversationTime(dateTimeParser.parse(dateCreated), today)
        }.getOrDefault("")

        return ConversationWsEvent.NewMessage(
            conversationId = conversationId,
            lastMessageText = messageDto.text.orEmpty(),
            lastMessageKind = messageDto.kind.toDomain(),
            lastMessageAttachmentCount = messageDto.attachments.size,
            formattedTime = formattedTime,
            dateUpdated = dateCreated
        )
    }
}
