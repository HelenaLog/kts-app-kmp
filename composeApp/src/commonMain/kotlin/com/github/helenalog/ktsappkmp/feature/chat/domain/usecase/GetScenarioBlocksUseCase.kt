package com.github.helenalog.ktsappkmp.feature.chat.domain.usecase

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlock
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlockType
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.BotActionRepository

class GetScenarioBlocksUseCase(private val repository: BotActionRepository) {
    suspend operator fun invoke(scenarioId: String): Result<List<ScenarioBlock>> =
        repository.getBlocks(scenarioId).map { blocks ->
            blocks.filter { it.type == ScenarioBlockType.ACTION }
        }
}
