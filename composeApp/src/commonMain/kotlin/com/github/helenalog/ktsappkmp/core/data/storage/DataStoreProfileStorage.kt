package com.github.helenalog.ktsappkmp.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile
import kotlinx.coroutines.flow.first

class DataStoreProfileStorage(
    private val dataStore: DataStore<Preferences>
) : ProfileStorage {

    override suspend fun save(profile: Profile): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs ->
            prefs[PROFILE_ID] = profile.id
            prefs[PROFILE_NAME] = profile.name
            prefs[PROFILE_EMAIL] = profile.email
            prefs[PROFILE_AVATAR] = profile.avatarUrl.orEmpty()
        }
    }

    @Suppress("ReturnCount")
    override suspend fun getProfile(): Profile? {
        val prefs = dataStore.data.first()
        val id = prefs[PROFILE_ID] ?: return null
        val name = prefs[PROFILE_NAME] ?: return null
        val email = prefs[PROFILE_EMAIL] ?: return null
        val avatar = prefs[PROFILE_AVATAR]
        return Profile(
            id = id,
            name = name,
            email = email,
            avatarUrl = avatar
        )
    }

    override suspend fun clear(): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs ->
            prefs.remove(PROFILE_ID)
            prefs.remove(PROFILE_NAME)
            prefs.remove(PROFILE_EMAIL)
            prefs.remove(PROFILE_AVATAR)
        }
    }

    private companion object {
        val PROFILE_ID = stringPreferencesKey("profile_id")
        val PROFILE_NAME = stringPreferencesKey("profile_name")
        val PROFILE_EMAIL = stringPreferencesKey("profile_email")
        val PROFILE_AVATAR = stringPreferencesKey("profile_avatar")
    }
}
