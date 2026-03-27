package com.github.helenalog.ktsappkmp.feature.chat.presentation.mapper

import com.github.helenalog.ktsappkmp.core.utils.DateFormatter
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachment
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatAttachmentType
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ChatMessage
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatAttachmentUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatListItemUi
import com.github.helenalog.ktsappkmp.feature.chat.presentation.model.ChatMessageUi
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class ChatUiMapper(
    private val avatarMapper: UserAvatarUiMapper
) {

    fun mapToList(
        messages: List<ChatMessage>,
        userName: String,
        userPhotoUrl: String?,
    ): List<ChatListItemUi> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return buildList {
            var lastDate: LocalDate? = null
            messages.forEach { message ->
                val localDate = message.date?.let { DateFormatter.parseToLocalDate(it) }
                if (localDate != null && localDate != lastDate) {
                    add(
                        ChatListItemUi.DateHeader(
                            text = DateFormatter.formatDateLabel(message.date, today),
                            dateKey = message.date,
                        )
                    )
                    lastDate = localDate
                }
                add(ChatListItemUi.Message(mapMessage(message, userName, userPhotoUrl)))
            }
        }
    }

    private fun mapMessage(
        domain: ChatMessage,
        userName: String,
        userPhotoUrl: String?,
    ): ChatMessageUi = ChatMessageUi(
        id = domain.id,
        text = normalizeServiceText(domain.text),
        formattedTime = domain.time.orEmpty(),
        isOutgoing = domain.kind == MessageKind.USER,
        kind = domain.kind,
        userName = userName,
        userPhotoUrl = userPhotoUrl.orEmpty(),
        attachments = domain.attachments.map { it.toUi() },
        avatar = avatarMapper.map(userName, userPhotoUrl)
    )

    fun mapAttachment(domain: ChatAttachment): ChatAttachmentUi = domain.toUi()

    fun mapAttachmentToDomain(ui: ChatAttachmentUi): ChatAttachment = when (ui) {
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
        "stop_bot" -> "Бот остановлен и не будет реагировать на сообщения пользователя"
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