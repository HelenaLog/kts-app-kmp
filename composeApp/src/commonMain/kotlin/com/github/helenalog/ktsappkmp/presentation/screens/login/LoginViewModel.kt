package com.github.helenalog.ktsappkmp.presentation.screens.login

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.data.repository.LoginRepositoryImpl
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Initial) {
    private val repository = LoginRepositoryImpl()

    fun onEmailChanged(value: String) = updateState {
        copy(
            email = value,
            isLoginButtonActive = value.isNotBlank() && password.isNotBlank(),
            error = ""
        )
    }

    fun onPasswordChanged(value: String) = updateState {
        copy(
            password = value,
            isLoginButtonActive = email.isNotBlank() && value.isNotBlank(),
            error = ""
        )
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            val (email, password) = state.value
            repository.login(email, password)
                .onSuccess { sendEvent(LoginUiEvent.LoginSuccessEvent) }
                .onFailure { updateState { copy(error = it.message ?: "Unknown error") } }
        }
    }
}