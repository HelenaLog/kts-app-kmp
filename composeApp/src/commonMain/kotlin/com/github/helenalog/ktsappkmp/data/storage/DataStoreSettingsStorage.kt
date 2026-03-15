package com.github.helenalog.ktsappkmp.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.github.helenalog.ktsappkmp.utils.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreSettingsStorage(
    private val dataStore: DataStore<Preferences> = DataStoreProvider.instance
) : SettingsStorage {

    private companion object {
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
    }

    override fun isOnboardingDone(): Flow<Boolean> =
        dataStore.data.map { prefs -> prefs[ONBOARDING_DONE] ?: false }

    override suspend fun completeOnboarding(): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs -> prefs[ONBOARDING_DONE] = true }
    }
}