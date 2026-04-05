package com.github.helenalog.ktsappkmp.feature.chat.data.remote.api

import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ScenarioBlockListDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ScenarioListDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.SpecialVarListDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.request.BotActionRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class BotActionApi(private val client: HttpClient) {

    suspend fun startBot(request: BotActionRequestDto): ApiResponse<Unit> =
        client.post("api/conversations/start_bot") {
            setBody(request)
        }.body()

    suspend fun stopBot(request: BotActionRequestDto): ApiResponse<Unit> =
        client.post("api/conversations/stop_bot") {
            setBody(request)
        }.body()

    suspend fun getScenarios(
        kind: String = "common",
        limit: Int = DEFAULT_LIMIT,
        offset: Int = 0,
    ): ApiResponse<ScenarioListDto> =
        client.get("api/scenarios/list") {
            parameter("kind", kind)
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getBlocks(scenarioId: String): ApiResponse<ScenarioBlockListDto> =
        client.get("api/blocks/list") {
            parameter("scenario_id", scenarioId)
        }.body()

    suspend fun getSpecialVars(): ApiResponse<SpecialVarListDto> =
        client.get("api/vars/list_special").body()

    private companion object {
        const val DEFAULT_LIMIT = 20
    }
}

