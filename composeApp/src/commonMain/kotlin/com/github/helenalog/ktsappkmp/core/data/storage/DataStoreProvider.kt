package com.github.helenalog.ktsappkmp.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val DATA_STORE_FILE_NAME = "settings.preferences_pb"

object DataStoreProvider {
    val instance: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { "${getFilesDir()}/$DATA_STORE_FILE_NAME".toPath() }
        )
    }
}