package com.github.helenalog.ktsappkmp.feature.chat.data.repository

import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.chat.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.api.BotActionApi
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.BotActionRequestDto
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.Scenario
import com.github.helenalog.ktsappkmp.feature.chat.domain.model.ScenarioBlock
import com.github.helenalog.ktsappkmp.feature.chat.domain.repository.BotActionRepository
import kotlinx.serialization.json.Json

class BotActionRepositoryImpl(
    private val api: BotActionApi,
    private val json: Json
) : BotActionRepository {

    override suspend fun startBot(conversationId: Long, userId: String): Result<Unit> =
        suspendRunCatching {
            api.startBot(BotActionRequestDto(conversationId = conversationId, userId = userId))
            Unit
        }

    override suspend fun stopBot(conversationId: Long, userId: String): Result<Unit> =
        suspendRunCatching {
            api.stopBot(BotActionRequestDto(conversationId = conversationId, userId = userId))
            Unit
        }

    override suspend fun getScenarios(): Result<List<Scenario>> = suspendRunCatching {
        api.getScenarios().data.scenarios.map { it.toDomain() }
    }

    override suspend fun getBlocks(scenarioId: String): Result<List<ScenarioBlock>> =
        suspendRunCatching {
            val specialVars = api.getSpecialVars().data.vars
                .associate { it.key to it.title }
            api.getBlocks(scenarioId).data.blocks
                .filter { it.kind != "first_message" }
                .map { it.toDomain(json, specialVars) }
        }

    override suspend fun runScenario(
        conversationId: Long,
        blockId: String,
    ): Result<Unit> = suspendRunCatching {
        api.startBot(
            BotActionRequestDto(
                conversationId = conversationId,
                blockId = blockId,
            )
        )
        Unit
    }
}
