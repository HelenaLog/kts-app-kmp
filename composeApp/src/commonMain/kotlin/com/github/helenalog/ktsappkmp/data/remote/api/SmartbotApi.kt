package com.github.helenalog.ktsappkmp.data.remote.api

import com.github.helenalog.ktsappkmp.data.remote.dto.response.ApiResponse
import com.github.helenalog.ktsappkmp.data.remote.dto.response.AuthInfoResponse
import com.github.helenalog.ktsappkmp.data.remote.dto.response.CabinetResponse
import com.github.helenalog.ktsappkmp.data.remote.dto.response.ConversationsResponse
import com.github.helenalog.ktsappkmp.data.remote.dto.response.ProjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

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

    suspend fun getAuthInfo(): ApiResponse<AuthInfoResponse> {
        return httpClient.get("api/auth/info").body()
    }

    suspend fun getCabinets(): ApiResponse<CabinetResponse> {
        return httpClient.get("api/cabinets/list").body()
    }

    suspend fun getProjects(): ApiResponse<ProjectResponse> {
        return httpClient.get("api/projects/get").body()
    }
}