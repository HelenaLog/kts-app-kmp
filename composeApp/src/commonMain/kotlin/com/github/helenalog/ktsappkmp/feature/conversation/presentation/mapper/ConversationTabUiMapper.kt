package com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper

import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationTab
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.model.ConversationTabUi

class ConversationTabUiMapper {

    fun map(selected: ConversationTab, unreadCount: Int): List<ConversationTabUi> =
        ConversationTab.entries.map { tab ->
            ConversationTabUi(
                id = tab,
                label = toLabel(tab, unreadCount),
                isSelected = tab == selected
            )
        }

    fun toIsRead(tab: ConversationTab): Boolean? = when (tab) {
        ConversationTab.ALL -> null
        ConversationTab.UNREAD -> false
    }

    fun toFilter(tab: ConversationTab): (isRead: Boolean) -> Boolean = when (tab) {
        ConversationTab.ALL -> { _ -> true }
        ConversationTab.UNREAD -> { isRead -> !isRead }
    }

    private fun toLabel(tab: ConversationTab, unreadCount: Int): String = when (tab) {
        ConversationTab.ALL -> LABEL_ALL
        ConversationTab.UNREAD -> if (unreadCount > 0) "$LABEL_UNREAD ($unreadCount)"
        else LABEL_UNREAD
    }

    private companion object {
        const val LABEL_ALL = "Все обращения"
        const val LABEL_UNREAD = "Ждут ответа"
    }
}
