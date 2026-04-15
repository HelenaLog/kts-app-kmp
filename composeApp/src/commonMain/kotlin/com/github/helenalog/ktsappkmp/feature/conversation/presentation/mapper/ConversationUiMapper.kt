package com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Conversation
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.MessageKind
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi

class ConversationUiMapper(
    private val avatarMapper: UserAvatarUiMapper = UserAvatarUiMapper()
) {
    fun mapList(conversations: List<Conversation>): List<ConversationUi> =
        conversations.map { map(it) }

    private fun map(conversation: Conversation): ConversationUi = ConversationUi(
        id = conversation.id,
        avatar = avatarMapper.map(conversation.userName, conversation.photoUrl),
        userName = conversation.userName,
        formattedTime = conversation.formattedTime,
        dateUpdated = conversation.dateUpdated,
        isRead = conversation.isRead,
        lastMessageKind = conversation.lastMessageKind,
        lastMessageText = normalizeLastMessageText(
            kind = conversation.lastMessageKind,
            text = conversation.lastMessageText
        ),
        lastMessageAttachmentCount = conversation.lastMessageAttachmentCount,
        channelKind = conversation.channel.kind,
        userId = conversation.userId,
    )

    private fun normalizeLastMessageText(kind: MessageKind?, text: String): String {
        if (kind != MessageKind.SERVICE) return text
        return when (text.trim()) {
            "stop_bot" -> "Бот остановлен и не будет реагировать на сообщения пользователя. " +
                    "Вы можете задать время для автоматического перевода диалога с оператора на бота в настройках"
            "start_bot" -> "Пользователь переведён на бота"
            else -> text
        }
    }
}
