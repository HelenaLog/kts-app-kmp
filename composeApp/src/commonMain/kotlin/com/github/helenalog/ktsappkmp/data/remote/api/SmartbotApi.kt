package com.github.helenalog.ktsappkmp.data.remote.api

import com.github.helenalog.ktsappkmp.data.remote.dto.ApiResponse
import com.github.helenalog.ktsappkmp.data.remote.dto.ConversationsResponse
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class SmartbotApi(private val httpClient: HttpClient) {

    suspend fun getConversations(
        limit: Int = 20,
        offset: Int = 0,
        query: String = ""
    ): ApiResponse<ConversationsResponse> {
        return httpClient.get("api/conversations/list") {
            parameter("limit", limit)
            parameter("offset", offset)
            if (query.isNotBlank()) parameter("q", query)
        }.body()
    }
}