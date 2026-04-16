package com.github.helenalog.ktsappkmp.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.github.helenalog.ktsappkmp.core.data.local.AppDatabase
import com.github.helenalog.ktsappkmp.core.data.storage.SessionStorage
import com.github.helenalog.ktsappkmp.core.data.storage.SessionStorageImpl
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.UrlLauncher
import okio.Path.Companion.toOkioPath
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "app_database.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single<SessionStorage> { SessionStorageImpl(androidContext()) }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                androidContext()
                    .filesDir
                    .resolve("app_settings.preferences_pb")
                    .toOkioPath()
            }
        )
    }
    single { UrlLauncher(androidContext()) }
}
