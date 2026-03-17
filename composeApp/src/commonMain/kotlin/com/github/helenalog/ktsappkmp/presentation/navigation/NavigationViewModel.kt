package com.github.helenalog.ktsappkmp.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.data.storage.DataStoreSettingsStorage
import com.github.helenalog.ktsappkmp.data.storage.SessionProvider
import com.github.helenalog.ktsappkmp.data.storage.SettingsStorage
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class NavigationViewModel(
) : BaseViewModel<NavigationState, NavigationEvent>(NavigationState.Loading) {
    private val settings: SettingsStorage = DataStoreSettingsStorage()
    private val sessionProvider = SessionProvider.instance

    init {
        observeAppStatus()
        setupUnauthorizedHandler()
    }

    private fun observeAppStatus() {
        viewModelScope.launch {
            settings.isOnboardingDone().collect { isOnboardingDone ->
                val destination = when {
                    !isOnboardingDone -> Screen.Onboarding
                    sessionProvider.getSession() != null -> Screen.Tabs
                    else -> Screen.Login
                }
                updateState { NavigationState.Content(destination) }
            }
        }
    }

    private fun setupUnauthorizedHandler() {
        Networking.onUnauthorized = {
            sendEvent(NavigationEvent.NavigateToLogin)
        }
    }
}