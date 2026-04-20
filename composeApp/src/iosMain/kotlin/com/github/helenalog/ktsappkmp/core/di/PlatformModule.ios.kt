package com.github.helenalog.ktsappkmp.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.github.helenalog.ktsappkmp.core.data.local.AppDatabase
import com.github.helenalog.ktsappkmp.core.data.local.getDatabaseBuilder
import com.github.helenalog.ktsappkmp.core.data.storage.SessionStorage
import com.github.helenalog.ktsappkmp.core.data.storage.SessionStorageImpl
import com.github.helenalog.ktsappkmp.core.presentation.ui.components.UrlLauncher
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule = module {

    single<AppDatabase> {
        getDatabaseBuilder(context = Unit)
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single<SessionStorage> { SessionStorageImpl() }

    single<DataStore<Preferences>> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        val path = requireNotNull(documentDirectory).path + "/app_settings.preferences_pb"
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { path.toPath() }
        )
    }

    single { UrlLauncher() }
}
