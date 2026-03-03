package com.github.helenalog.ktsappkmp.domain.repository

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<Unit>
}