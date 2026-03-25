package com.github.helenalog.ktsappkmp.presentation.screens.onboarding

sealed interface OnboardingUiEvent {
    data object OnboardingCompleted : OnboardingUiEvent
}