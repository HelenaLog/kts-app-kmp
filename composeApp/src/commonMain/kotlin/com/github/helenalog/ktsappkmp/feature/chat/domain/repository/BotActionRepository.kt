package com.github.helenalog.ktsappkmp.feature.chat.domain.repository

import com.github.helenalog.ktsappkmp.feature.chat.domain.model.Scenario
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlock

interface BotActionRepository {
    suspend fun startBot(conversationId: Long, userId: String): Result<Unit>
    suspend fun stopBot(conversationId: Long, userId: String): Result<Unit>
    suspend fun getScenarios(): Result<List<Scenario>>
    suspend fun runScenario(conversationId: Long, blockId: String): Result<Unit>
    suspend fun getBlocks(scenarioId: String): Result<List<ScenarioBlock>>
}
