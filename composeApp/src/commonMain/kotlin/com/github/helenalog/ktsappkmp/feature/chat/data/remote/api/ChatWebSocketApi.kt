package com.github.helenalog.ktsappkmp.feature.chat.data.remote.api

import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.response.WsConnectionTokenResponse
import com.github.helenalog.ktsappkmp.feature.chat.data.remote.response.WsSubscriptionTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ChatWebSocketApi(private val client: HttpClient) {

    suspend fun obtainSubscriptionToken(): ApiResponse<WsSubscriptionTokenResponse> =
        client.get("api/conversations/obtain_subscription_token").body()

    suspend fun obtainWsAuthToken(): ApiResponse<WsConnectionTokenResponse> =
        client.get("api/auth/obtain_ws_auth_token").body()
}
