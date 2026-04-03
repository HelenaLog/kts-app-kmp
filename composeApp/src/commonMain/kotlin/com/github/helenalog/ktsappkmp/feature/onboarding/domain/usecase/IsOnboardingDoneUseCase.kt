package com.github.helenalog.ktsappkmp.feature.onboarding.domain.usecase

import com.github.helenalog.ktsappkmp.core.data.storage.SettingsStorage
import kotlinx.coroutines.flow.Flow

class IsOnboardingDoneUseCase(private val storage: SettingsStorage) {
    operator fun invoke(): Flow<Boolean> = storage.isOnboardingDone()
}
