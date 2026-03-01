package com.github.helenalog.ktsappkmp.screens.login.presentation

import com.github.helenalog.ktsappkmp.common.BaseViewModel
import com.github.helenalog.ktsappkmp.screens.login.presentation.models.LoginUiEvent
import com.github.helenalog.ktsappkmp.screens.login.presentation.models.LoginUiState

class LoginViewModel: BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Initial) {
    fun onEmailChanged(value: String) {
        updateState { copy(email = value) }
    }

    fun onPasswordChanged(value: String) {
        updateState { copy(password = value) }
    }

    fun onLoginClicked() {
        val currentState = state.value
        if (currentState.email == "admin@gmail.com" && currentState.password == "1234") {
            sendEvent(LoginUiEvent.LoginSuccessEvent)
        } else {
            updateState { copy(error = "Invalid credentials") }
        }
    }
}