package com.github.helenalog.ktsappkmp.feature.login.domain.usecase

import com.github.helenalog.ktsappkmp.feature.login.domain.repository.LoginRepository

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(
        username: String,
        password: String,
        captchaToken: String,
    ): Result<Unit> = repository.login(username, password, captchaToken)
}
