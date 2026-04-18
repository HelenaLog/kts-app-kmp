package com.github.helenalog.ktsappkmp.core.data.activechat.repository

import com.github.helenalog.ktsappkmp.core.domain.activechat.repository.ActiveChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ActiveChatRepositoryImpl : ActiveChatRepository {
    private val _activeConversationId = MutableStateFlow<Long?>(null)
    override val activeConversationId: StateFlow<Long?> = _activeConversationId.asStateFlow()

    override fun onChatOpened(conversationId: Long) {
        _activeConversationId.value = conversationId
    }

    override fun onChatClosed() {
        _activeConversationId.value = null
    }
}
