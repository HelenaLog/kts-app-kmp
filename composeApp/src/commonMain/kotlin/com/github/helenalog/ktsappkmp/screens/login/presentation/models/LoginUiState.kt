package com.github.helenalog.ktsappkmp.screens.login.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val username: String,
    val password: String,
    val isLoginButtonActive: Boolean,
    val error: String,
) {
    companion object {
        val Initial = LoginUiState(
            username = "",
            password = "",
            isLoginButtonActive = false,
            error = ""
        )
    }
}