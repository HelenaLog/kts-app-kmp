package com.github.helenalog.ktsappkmp.core.data.project.remote.api

import com.github.helenalog.ktsappkmp.core.data.project.remote.response.ProjectResponse
import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProjectApi(private val client: HttpClient) {
    suspend fun getProjects(): ApiResponse<ProjectResponse> =
        client.get("api/projects/list").body()
}
