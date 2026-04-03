package com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper

import com.github.helenalog.ktsappkmp.core.utils.DateTimeParser
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachmentType
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatMessageUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

class ChatUiMapper(
    private val avatarMapper: UserAvatarUiMapper,
    private val dateTimeParser: DateTimeParser,
) {

    fun mapMessages(
        messages: List<ChatMessage>,
        userName: String,
        userPhotoUrl: String?,
    ): List<ChatListItemUi.Message> = messages.map {
        ChatListItemUi.Message(mapMessage(it, userName, userPhotoUrl))
    }

    fun addDateHeaders(
        messages: List<ChatListItemUi.Message>
    ): List<ChatListItemUi> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val sorted = messages
            .distinctBy { it.data.id }
            .sortedByDescending { it.data.createdAt }

        return buildList {
            sorted.forEachIndexed { index, item ->
                add(item)
                val nextDate = sorted.getOrNull(index + 1)?.data?.date
                if (item.data.date != nextDate) {
                    add(
                        ChatListItemUi.DateHeader(
                            text = dateTimeParser.formatDateLabel(item.data.createdAt, today),
                            dateKey = item.data.date.orEmpty()
                        )
                    )
                }
            }
        }
    }

    fun mapMessage(
        domain: ChatMessage,
        userName: String,
        userPhotoUrl: String?,
    ): ChatMessageUi = ChatMessageUi(
        id = domain.id,
        text = normalizeServiceText(domain.text),
        formattedTime = domain.time.orEmpty(),
        date = domain.date,
        isOutgoing = domain.kind == MessageKind.USER,
        kind = domain.kind,
        userName = userName,
        userPhotoUrl = userPhotoUrl.orEmpty(),
        attachments = domain.attachments.map { it.toUi() },
        avatar = avatarMapper.map(userName, userPhotoUrl),
        createdAt = domain.createdAt,
    )

    fun mapAttachment(domain: ChatAttachment): ChatAttachmentUi = domain.toUi()

    fun toDomain(ui: ChatAttachmentUi): ChatAttachment = when (ui) {
        is ChatAttachmentUi.Image -> ChatAttachment(
            id = ui.id,
            type = ChatAttachmentType.IMAGE,
            url = ui.url
        )

        is ChatAttachmentUi.File -> ChatAttachment(
            id = ui.id,
            type = ChatAttachmentType.FILE,
            name = ui.name
        )
    }

    private fun normalizeServiceText(text: String?): String = when (text?.trim()) {
        "stop_bot" -> "Бот остановлен и не будет реагировать на сообщения пользователя. " +
                "Вы можете задать время для автоматического перевода диалога с оператора на бота в настройках"

        "start_bot" -> "Пользователь переведён на бота"
        else -> text.orEmpty()
    }

    private fun ChatAttachment.toUi(): ChatAttachmentUi = when (type) {
        ChatAttachmentType.IMAGE -> ChatAttachmentUi.Image(id = id, url = url.orEmpty())
        ChatAttachmentType.FILE -> ChatAttachmentUi.File(
            id = id,
            name = name ?: "Файл",
            typeLabel = formatTypeLabel(mimeType),
            sizeLabel = formatSize(size ?: 0L),
            extension = mimeType?.substringAfterLast('/').orEmpty(),
        )
    }

    private fun formatTypeLabel(mimeType: String?): String {
        val raw = mimeType?.substringBefore('/')?.uppercase() ?: "FILE"
        return when (raw) {
            "DOCUMENT" -> "DOC"
            "AUDIO" -> "AUD"
            "VIDEO" -> "VID"
            "IMAGE" -> "IMG"
            else -> raw.take(3)
        }
    }

    private fun formatSize(bytes: Long): String = when {
        bytes >= 1024 * 1024 -> "${bytes / (1024 * 1024)} MB"
        bytes >= 1024 -> "${bytes / 1024} KB"
        else -> "$bytes B"
    }
}
