package com.github.helenalog.ktsappkmp.feature.conversation.data.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.domain.repository.ConversationWsEvent
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationUi

class ConversationWsEventMapper {

    fun applyNewMessage(
        current: List<ConversationUi>,
        event: ConversationWsEvent.NewMessage,
        tabFilter: (isRead: Boolean) -> Boolean
    ): List<ConversationUi> {
        val index = current.indexOfFirst { it.id == event.conversationId }

        return if (index == -1) {
            if (tabFilter(event.isRead)) {
                buildList {
                    addAll(current)
                }
            } else {
                current
            }
        } else {
            val updated = current[index].copy(
                lastMessageText = event.lastMessageText,
                lastMessageKind = event.lastMessageKind,
                lastMessageAttachmentCount = event.lastMessageAttachmentCount,
                formattedTime = event.formattedTime,
                dateUpdated = event.dateUpdated,
                isRead = event.isRead,
            )
            if (!tabFilter(updated.isRead)) {
                current.filterIndexed { i, _ -> i != index }
            } else {
                buildList {
                    add(updated)
                    addAll(current.filterIndexed { i, _ -> i != index })
                }
            }
        }
    }
}
