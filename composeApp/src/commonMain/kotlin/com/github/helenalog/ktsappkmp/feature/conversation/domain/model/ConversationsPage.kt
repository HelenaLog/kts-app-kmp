package com.github.helenalog.ktsappkmp.feature.conversation.domain.model

data class ConversationsPage(
    val conversations: List<Conversation>,
    val hasMore: Boolean
) {
    val unreadCount: Int get() = conversations.count { !it.isRead }
}
