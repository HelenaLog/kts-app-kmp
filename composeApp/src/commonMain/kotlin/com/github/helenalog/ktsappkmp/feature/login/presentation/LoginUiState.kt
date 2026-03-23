package com.github.helenalog.ktsappkmp.feature.login.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val email: String,
    val password: String,
    val error: String,
    val captchaToken: String = "",
    val isLoading: Boolean = false,
) {
    val isLoginButtonActive: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && captchaToken.isNotBlank()

    companion object {
        val Initial = LoginUiState(
            email = "",
            password = "",
            error = ""
        )
    }
}