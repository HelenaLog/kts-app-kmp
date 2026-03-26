package com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper

import com.github.helenalog.ktsappkmp.core.data.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.feature.conversation.domain.model.Conversation
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi

class ConversationUiMapper(
    private val avatarMapper: UserAvatarUiMapper = UserAvatarUiMapper()
) {
    fun mapList(conversations: List<Conversation>): List<ConversationUi> =
        conversations.map { map(it) }

    private fun map(conversation: Conversation): ConversationUi {
        return ConversationUi(
            id = conversation.id,
            avatar = avatarMapper.map(conversation.userName, conversation.photoUrl),
            userName = conversation.userName,
            formattedTime = formatTime(conversation.dateUpdated),
            isRead = conversation.isRead,
            channelKind = conversation.channelKind,
            lastMessageKind = conversation.lastMessageKind,
            lastMessageText = conversation.lastMessageText,
        )
    }
    private fun formatTime(dateUpdated: String): String = try {
        dateUpdated.substring(11, 16)
    } catch (e: Exception) {
        ""
    }
}