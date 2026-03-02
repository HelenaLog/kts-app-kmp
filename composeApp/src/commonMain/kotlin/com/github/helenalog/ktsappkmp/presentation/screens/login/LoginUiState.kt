package com.github.helenalog.ktsappkmp.presentation.screens.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val email: String,
    val password: String,
    val isLoginButtonActive: Boolean,
    val error: String,
) {
    companion object {
        val Initial = LoginUiState(
            email = "",
            password = "",
            isLoginButtonActive = false,
            error = ""
        )
    }
}