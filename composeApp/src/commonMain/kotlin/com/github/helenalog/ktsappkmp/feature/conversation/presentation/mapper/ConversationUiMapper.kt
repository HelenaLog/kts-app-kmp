package com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper

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
            formattedTime = conversation.formattedTime,
            isRead = conversation.isRead,
            lastMessageKind = conversation.lastMessageKind,
            lastMessageText = conversation.lastMessageText,
            channelKind = conversation.channel.kind,
            userId = conversation.userId,
        )
    }
}
