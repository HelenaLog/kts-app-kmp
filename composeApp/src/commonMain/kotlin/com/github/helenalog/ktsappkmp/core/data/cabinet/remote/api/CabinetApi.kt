package com.github.helenalog.ktsappkmp.core.data.cabinet.remote.api

import com.github.helenalog.ktsappkmp.core.data.cabinet.remote.response.CabinetResponse
import com.github.helenalog.ktsappkmp.core.data.remote.response.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CabinetApi(private val client: HttpClient) {
    suspend fun getCabinets(): ApiResponse<CabinetResponse> =
        client.get("api/cabinets/list").body()
}
