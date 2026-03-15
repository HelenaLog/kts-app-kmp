package com.github.helenalog.ktsappkmp.domain.model

data class ConversationsPage(
    val conversations: List<Conversation>,
    val hasMore: Boolean
)