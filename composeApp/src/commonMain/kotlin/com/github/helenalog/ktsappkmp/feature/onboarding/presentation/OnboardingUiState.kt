package com.github.helenalog.ktsappkmp.feature.onboarding.presentation

import com.github.helenalog.ktsappkmp.feature.onboarding.domain.model.OnboardingPage
import com.github.helenalog.ktsappkmp.feature.onboarding.domain.model.pages

data class OnboardingUiState(
    val pages: List<OnboardingPage> = emptyList(),
    val currentPage: Int = 0,
) {
    val totalPages: Int get() = pages.size
    val isFirstPage: Boolean get() = currentPage == 0
    val isLastPage: Boolean get() = currentPage == totalPages - 1
    val currentPageContent: OnboardingPage? get() = pages.getOrNull(currentPage)

    companion object {
        val Initial = OnboardingUiState(pages = pages)
    }
}