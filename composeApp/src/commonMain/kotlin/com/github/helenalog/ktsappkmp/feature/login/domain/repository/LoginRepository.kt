package com.github.helenalog.ktsappkmp.feature.login.domain.repository

interface LoginRepository {
    suspend fun login(
        username: String,
        password: String,
        captchaToken: String
    ): Result<Unit>
}