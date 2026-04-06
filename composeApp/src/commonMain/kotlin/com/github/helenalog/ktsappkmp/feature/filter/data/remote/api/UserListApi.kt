package com.github.helenalog.ktsappkmp.feature.filter.data.remote.api

import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import com.github.helenalog.ktsappkmp.feature.filter.data.remote.dto.UserListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class UserListApi(private val client: HttpClient) {
    suspend fun getUserLists(): ApiResponse<UserListResponseDto> =
        client.get("api/lists/list") {
            parameter("type", "users")
        }.body()
}
