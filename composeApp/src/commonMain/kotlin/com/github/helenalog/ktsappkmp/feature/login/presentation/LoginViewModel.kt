package com.github.helenalog.ktsappkmp.feature.login.presentation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.WorkspaceBootstrapper
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val workspaceBootstrapper: WorkspaceBootstrapper
) : BaseViewModel<LoginUiState, LoginUiEvent>(LoginUiState.Initial) {

    fun onEmailChanged(value: String) = updateState {
        copy(
            email = value,
            error = null
        )
    }

    fun onPasswordChanged(value: String) = updateState {
        copy(
            password = value,
            error = null
        )
    }

    fun onCaptchaTokenReceived(token: String) {
        updateState { copy(captchaToken = token, error = null) }
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            val state = state.value
            loginUseCase(
                username = state.email,
                password = state.password,
                captchaToken = state.captchaToken,
            )
                .onSuccess { handleLoginSuccess() }
                .onFailure { handleFailure(it) }
        }
    }

    private suspend fun handleLoginSuccess() {
        workspaceBootstrapper.ensureActiveWorkspace()
            .onSuccess {
                updateState { copy(isLoading = false) }
                sendEvent(LoginUiEvent.LoginSuccessEvent)
            }
            .onFailure { handleFailure(it) }
    }

    private fun handleFailure(throwable: Throwable) {
        updateState {
            copy(
                isLoading = false,
                error = throwable.message ?: UNKNOWN_ERROR,
            )
        }
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}
