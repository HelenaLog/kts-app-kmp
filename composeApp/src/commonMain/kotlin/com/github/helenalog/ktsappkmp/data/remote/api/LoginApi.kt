package com.github.helenalog.ktsappkmp.data.remote.api

import com.github.helenalog.ktsappkmp.data.remote.dto.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class LoginApi(private val httpClient: HttpClient) {

    suspend fun login(
        email: String,
        password: String,
        captchaToken: String
    ): HttpResponse {
        return httpClient.post("api/auth/login") {
            setBody(
                LoginRequest(
                    email = email,
                    password = password,
                    captchaToken = captchaToken
                )
            )
        }
    }
}