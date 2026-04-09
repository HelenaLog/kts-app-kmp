package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.Scenario
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.BotActionRepository

class GetScenariosUseCase(private val repository: BotActionRepository) {
    suspend operator fun invoke(): Result<List<Scenario>> =
        repository.getScenarios()
}
