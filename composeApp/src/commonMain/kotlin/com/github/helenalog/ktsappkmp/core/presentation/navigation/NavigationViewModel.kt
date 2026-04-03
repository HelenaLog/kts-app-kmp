package com.github.helenalog.ktsappkmp.core.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.data.remote.network.OnUnauthorizedCallback
import com.github.helenalog.ktsappkmp.core.data.storage.SessionStorage
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.onboarding.domain.usecase.IsOnboardingDoneUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val sessionStorage: SessionStorage,
    private val isOnboardingDoneUseCase: IsOnboardingDoneUseCase,
    private val onUnauthorizedCallback: OnUnauthorizedCallback,
) : BaseViewModel<NavigationState, NavigationEvent>(NavigationState.Loading) {

    init {
        observeAppStatus()
        setupUnauthorizedHandler()
    }

    private fun observeAppStatus() {
        viewModelScope.launch {
            val isOnboardingDone = isOnboardingDoneUseCase().first()
            val destination = when {
                !isOnboardingDone -> Screen.Onboarding
                sessionStorage.getSession() != null -> Screen.Tabs
                else -> Screen.Login
            }
            updateState { NavigationState.Content(destination) }
        }
    }

    private fun setupUnauthorizedHandler() {
        onUnauthorizedCallback.onUnauthorized = {
            sendEvent(NavigationEvent.NavigateToLogin)
        }
    }
}