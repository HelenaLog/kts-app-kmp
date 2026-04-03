package com.github.helenalog.ktsappkmp.core.presentation.navigation.di

import com.github.helenalog.ktsappkmp.core.presentation.navigation.NavigationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val navigationModule = module {
    viewModel {
        NavigationViewModel(
            sessionStorage = get(),
            isOnboardingDoneUseCase = get(),
            onUnauthorizedCallback = get(),
        )
    }
}
