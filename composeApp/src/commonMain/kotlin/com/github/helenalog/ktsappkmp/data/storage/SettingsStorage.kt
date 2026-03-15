package com.github.helenalog.ktsappkmp.data.storage

import kotlinx.coroutines.flow.Flow

interface SettingsStorage {
    fun isOnboardingDone(): Flow<Boolean>
    suspend fun completeOnboarding(): Result<Unit>
}