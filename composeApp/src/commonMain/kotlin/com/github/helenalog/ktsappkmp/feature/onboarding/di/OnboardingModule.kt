package com.github.helenalog.ktsappkmp.feature.onboarding.di

import com.github.helenalog.ktsappkmp.feature.onboarding.domain.usecase.CompleteOnboardingUseCase
import com.github.helenalog.ktsappkmp.feature.onboarding.domain.usecase.IsOnboardingDoneUseCase
import com.github.helenalog.ktsappkmp.feature.onboarding.presentation.OnboardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {

    factory { CompleteOnboardingUseCase(get()) }
    factory { IsOnboardingDoneUseCase(get()) }

    viewModel { OnboardingViewModel(completeOnboardingUseCase = get()) }
}