package com.github.helenalog.ktsappkmp.feature.conversation.data.remote.api

import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import com.github.helenalog.ktsappkmp.feature.conversation.data.remote.response.ConversationsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ConversationApi(private val client: HttpClient) {

    suspend fun getConversations(
        limit: Int = 20,
        offset: Int = 0,
        query: String = "",
    ): ApiResponse<ConversationsResponse> =
        client.get("api/conversations/list") {
            parameter("limit", limit)
            parameter("offset", offset)
            if (query.isNotBlank()) parameter("q", query)
        }.body()
}
