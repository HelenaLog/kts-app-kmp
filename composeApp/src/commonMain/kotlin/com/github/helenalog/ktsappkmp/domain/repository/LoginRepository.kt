package com.github.helenalog.ktsappkmp.domain.repository

interface LoginRepository {
    fun login(username: String, password: String): Result<Unit>
}