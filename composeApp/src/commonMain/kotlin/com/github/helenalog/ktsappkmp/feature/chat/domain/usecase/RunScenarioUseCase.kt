package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.BotActionRepository

class RunScenarioUseCase(private val repository: BotActionRepository) {
    suspend operator fun invoke(
        conversationId: Long,
        blockId: String
    ): Result<Unit> = repository.runScenario(conversationId, blockId)
}
