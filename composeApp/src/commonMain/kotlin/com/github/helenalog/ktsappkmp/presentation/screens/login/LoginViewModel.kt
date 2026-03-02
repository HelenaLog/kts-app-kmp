package com.github.helenalog.ktsappkmp.presentation.screens.login

import com.github.helenalog.ktsappkmp.data.repository.LoginRepositoryImpl
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel

class LoginViewModel : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Companion.Initial) {
    private val repository = LoginRepositoryImpl()

    fun onEmailChanged(value: String) = updateState { copy(email = value) }

    fun onPasswordChanged(value: String) = updateState { copy(password = value) }

    fun onLoginClicked() {
        val (email, password) = state.value
        repository.login(email, password)
            .onSuccess { sendEvent(LoginUiEvent.LoginSuccessEvent) }
            .onFailure { updateState { copy(error = it.message ?: "Unknown error") } }
    }
}