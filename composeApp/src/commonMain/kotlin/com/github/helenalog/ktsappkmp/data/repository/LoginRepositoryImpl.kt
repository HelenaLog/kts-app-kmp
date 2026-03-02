package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.domain.repository.LoginRepository

class LoginRepositoryImpl : LoginRepository {
    override fun login(
        username: String,
        password: String
    ): Result<Unit> {
        return if (username == "admin@gmail.com" && password == "1234") {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Invalid login or password"))
        }
    }
}