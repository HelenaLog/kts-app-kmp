package com.github.helenalog.ktsappkmp.presentation.screens.login

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.data.repository.LoginRepositoryImpl
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Initial) {
    private val repository = LoginRepositoryImpl()

    fun onEmailChanged(value: String) = updateState {
        copy(
            email = value,
            error = ""
        )
    }

    fun onPasswordChanged(value: String) = updateState {
        copy(
            password = value,
            error = ""
        )
    }

    fun onCaptchaTokenReceived(token: String) {
        updateState { copy(captchaToken = token) }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            val state = state.value
            repository.login(state.email, state.password, state.captchaToken)
                .onSuccess {
                    updateState { copy(isLoading = false) }
                    sendEvent(LoginUiEvent.LoginSuccessEvent)
                }
                .onFailure { updateState { copy(isLoading = false, error = it.message ?: "Unknown error") } }
        }
    }
}