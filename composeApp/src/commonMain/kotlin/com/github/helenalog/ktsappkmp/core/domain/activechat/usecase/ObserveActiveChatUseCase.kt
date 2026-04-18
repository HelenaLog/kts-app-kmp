package com.github.helenalog.ktsappkmp.core.domain.activechat.usecase

import com.github.helenalog.ktsappkmp.core.domain.activechat.repository.ActiveChatRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveActiveChatUseCase(private val repository: ActiveChatRepository) {
    operator fun invoke(): StateFlow<Long?> = repository.activeConversationId
}
