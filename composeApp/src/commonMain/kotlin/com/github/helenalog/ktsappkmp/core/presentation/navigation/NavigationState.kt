package com.github.helenalog.ktsappkmp.core.presentation.navigation

sealed class NavigationState {
    object Loading : NavigationState()
    data class Content(val startDestination: Screen) : NavigationState()
}

sealed class NavigationEvent {
    object NavigateToLogin : NavigationEvent()
}