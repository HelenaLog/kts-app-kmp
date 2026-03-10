package com.github.helenalog.ktsappkmp.data.remote.dto

data class ConversationsPage(
    val conversations: List<ConversationDto>,
    val hasMore: Boolean
)