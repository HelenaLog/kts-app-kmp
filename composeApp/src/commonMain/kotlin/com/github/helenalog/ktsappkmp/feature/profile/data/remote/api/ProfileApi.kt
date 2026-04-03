package com.github.helenalog.ktsappkmp.feature.profile.data.remote.api

import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.response.AuthInfoResponse
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.response.CabinetResponse
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.response.ProjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProfileApi(private val client: HttpClient) {

    suspend fun getAuthInfo(): ApiResponse<AuthInfoResponse> =
        client.get("api/auth/info").body()

    suspend fun getCabinets(): ApiResponse<CabinetResponse> =
        client.get("api/cabinets/list").body()

    suspend fun getProjects(): ApiResponse<ProjectResponse> =
        client.get("api/projects/list").body()
}
