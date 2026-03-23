package com.github.helenalog.ktsappkmp.feature.onboarding.domain.usecase

import com.github.helenalog.ktsappkmp.core.data.storage.SettingsStorage

class CompleteOnboardingUseCase(private val storage: SettingsStorage) {
    suspend operator fun invoke(): Result<Unit> = storage.completeOnboarding()
}