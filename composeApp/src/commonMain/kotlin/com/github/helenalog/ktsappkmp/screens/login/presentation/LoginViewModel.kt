package com.github.helenalog.ktsappkmp.screens.login.presentation

import com.github.helenalog.ktsappkmp.common.BaseViewModel
import com.github.helenalog.ktsappkmp.screens.login.presentation.models.LoginUiEvent
import com.github.helenalog.ktsappkmp.screens.login.presentation.models.LoginUiState

class LoginViewModel : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Initial) {
    private val repository = LoginRepository()

    fun onEmailChanged(value: String) = updateState { copy(email = value) }

    fun onPasswordChanged(value: String) = updateState { copy(password = value) }

    fun onLoginClicked() {
        val (email, password) = state.value
        repository.login(email, password)
            .onSuccess { sendEvent(LoginUiEvent.LoginSuccessEvent) }
            .onFailure { updateState { copy(error = it.message ?: "Unknown error") } }
    }
}