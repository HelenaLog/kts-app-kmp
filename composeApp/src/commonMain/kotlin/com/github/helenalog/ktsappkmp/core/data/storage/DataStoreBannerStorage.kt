package com.github.helenalog.ktsappkmp.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class DataStoreBannerStorage(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : BannerStorage {
    override suspend fun read(key: String): Set<String> {
        val raw = dataStore.data.first()[stringPreferencesKey(key)] ?: return emptySet()
        return suspendRunCatching {
            json.decodeFromString<List<String>>(raw).toSet()
        }.getOrDefault(emptySet())
    }

    override suspend fun add(key: String, id: String) {
        val updated = read(key) + id
        suspendRunCatching {
            dataStore.edit { prefs ->
                prefs[stringPreferencesKey(key)] = json.encodeToString(updated.toList())
            }
        }
    }
}
