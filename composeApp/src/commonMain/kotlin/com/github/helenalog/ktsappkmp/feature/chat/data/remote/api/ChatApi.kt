package com.github.helenalog.ktsappkmp.feature.chat.data.remote.api

import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.dto.ConversationLiteDto
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.response.MessagesResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ChatApi(private val client: HttpClient) {

    suspend fun getConversationLite(
        conversationId: Long,
        userId: String,
    ): ApiResponse<ConversationLiteDto> =
        client.get("api/conversations/get_conversation_lite") {
            parameter("id", conversationId)
            parameter("user_id", userId)
        }.body()

    suspend fun getMessages(
        conversationId: Long,
        userId: String,
        channelId: String,
        limit: Int = DEFAULT_MESSAGES_LIMIT,
        fromId: String? = null,
    ): ApiResponse<MessagesResponseDto> =
        client.get("api/conversations/list_messages") {
            parameter("conversation_id", conversationId)
            parameter("user_id", userId)
            parameter("channel_id", channelId)
            parameter("limit", limit)
            fromId?.let { parameter("from_id", it) }
        }.body()

    private companion object {
        const val DEFAULT_MESSAGES_LIMIT  = 20
    }
}