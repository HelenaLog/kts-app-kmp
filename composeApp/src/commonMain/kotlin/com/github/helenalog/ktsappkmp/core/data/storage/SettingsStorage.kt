package com.github.helenalog.ktsappkmp.core.data.storage

import kotlinx.coroutines.flow.Flow

interface SettingsStorage {
    fun isOnboardingDone(): Flow<Boolean>
    suspend fun completeOnboarding(): Result<Unit>
}
