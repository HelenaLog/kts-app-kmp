package com.github.helenalog.ktsappkmp.core.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.core.data.storage.DataStoreSettingsStorage
import com.github.helenalog.ktsappkmp.core.data.storage.SessionProvider
import com.github.helenalog.ktsappkmp.core.data.storage.SettingsStorage
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

    BaseViewModel<NavigationState, NavigationEvent>(NavigationState.Loading) {
class NavigationViewModel :
    private val settings: SettingsStorage = DataStoreSettingsStorage()
    private val sessionProvider = SessionProvider.instance

    init {
        observeAppStatus()
        setupUnauthorizedHandler()
    }

    private fun observeAppStatus() {
        viewModelScope.launch {
            val isOnboardingDone = settings.isOnboardingDone().first()
            val destination = when {
                !isOnboardingDone -> Screen.Onboarding
                sessionProvider.getSession() != null -> Screen.Tabs
                else -> Screen.Login
            }
            updateState { NavigationState.Content(destination) }
        }
    }

    private fun setupUnauthorizedHandler() {
        Networking.onUnauthorized = {
            sendEvent(NavigationEvent.NavigateToLogin)
        }
    }
}