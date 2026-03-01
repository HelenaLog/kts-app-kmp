package com.github.helenalog.ktsappkmp.screens.login.presentation

import com.github.helenalog.ktsappkmp.common.BaseViewModel
import com.github.helenalog.ktsappkmp.screens.login.presentation.models.LoginUiState

class LoginViewModel: BaseViewModel<LoginUiState, Nothing>(LoginUiState.Initial) {
    fun onEmailChanged(value: String) {
        updateState { copy(email = value) }
    }

    fun onPasswordChanged(value: String) {
        updateState { copy(password = value) }
    }
}