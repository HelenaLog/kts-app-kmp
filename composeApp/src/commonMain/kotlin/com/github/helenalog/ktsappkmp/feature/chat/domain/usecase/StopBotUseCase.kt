package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.BotActionRepository

class StopBotUseCase(private val repository: BotActionRepository) {
    suspend operator fun invoke(conversationId: Long, userId: String): Result<Unit> =
        repository.stopBot(conversationId, userId)
}
