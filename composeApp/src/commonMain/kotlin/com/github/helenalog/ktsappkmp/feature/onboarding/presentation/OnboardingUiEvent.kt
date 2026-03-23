package com.github.helenalog.ktsappkmp.feature.onboarding.presentation

sealed interface OnboardingUiEvent {
    data object OnboardingCompleted : OnboardingUiEvent
}