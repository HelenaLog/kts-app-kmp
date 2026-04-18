package com.github.helenalog.ktsappkmp.core.domain.activechat.repository

import kotlinx.coroutines.flow.StateFlow

interface ActiveChatRepository {
    val activeConversationId: StateFlow<Long?>
    fun onChatOpened(conversationId: Long)
    fun onChatClosed()
}
