package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWsEvent
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi

class ConversationWsEventMapper {

    fun applyNewMessage(
        current: List<ConversationUi>,
        event: ConversationWsEvent.NewMessage,
    ): List<ConversationUi> {
        val index = current.indexOfFirst { it.id == event.conversationId }
        if (index == -1) return current

        val updated = current[index].copy(
            lastMessageText = event.lastMessageText,
            lastMessageKind = event.lastMessageKind,
            lastMessageAttachmentCount = event.lastMessageAttachmentCount,
            formattedTime = event.formattedTime,
            dateUpdated = event.dateUpdated,
            isRead = false
        )

        return buildList {
            add(updated)
            addAll(current.filterIndexed { i, _ -> i != index })
        }
    }
}
