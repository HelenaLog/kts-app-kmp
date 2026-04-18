package com.github.helenalog.ktsappkmp.core.domain.activechat.usecase

import com.github.helenalog.ktsappkmp.core.domain.activechat.repository.ActiveChatRepository

class TrackActiveChatUseCase(private val repository: ActiveChatRepository) {
    fun onOpened(conversationId: Long) = repository.onChatOpened(conversationId)
    fun onClosed() = repository.onChatClosed()
}
