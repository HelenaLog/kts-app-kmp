package com.github.helenalog.ktsappkmp.feature.onboarding.presentation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.data.storage.DataStoreSettingsStorage
import com.github.helenalog.ktsappkmp.core.data.storage.SettingsStorage
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.onboarding.domain.usecase.CompleteOnboardingUseCase
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : BaseViewModel<OnboardingUiState, OnboardingUiEvent>(OnboardingUiState.Initial) {

    fun nextPage() {
        if (state.value.isLastPage) {
            viewModelScope.launch {
                completeOnboardingUseCase()
                sendEvent(OnboardingUiEvent.OnboardingCompleted)
            }
        } else {
            updateState { copy(currentPage = currentPage + 1) }
        }
    }

    fun previousPage() {
        if (!state.value.isFirstPage) {
            updateState { copy(currentPage = currentPage - 1) }
        }
    }

    fun onPageSelected(page: Int) {
        if (page in 0 until state.value.totalPages) {
            updateState { copy(currentPage = page) }
        }
    }
}
