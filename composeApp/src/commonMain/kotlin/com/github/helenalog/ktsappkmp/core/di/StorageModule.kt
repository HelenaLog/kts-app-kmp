package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.storage.DataStoreSettingsStorage
import com.github.helenalog.ktsappkmp.core.data.storage.SessionManager
import com.github.helenalog.ktsappkmp.core.data.storage.SettingsStorage
import org.koin.dsl.module

val storageModule = module {
    single<SettingsStorage> { DataStoreSettingsStorage(get()) }

    single {
        SessionManager(
            sessionStorage = get(),
            conversationDao = get(),
            profileDao = get(),
            projectDao = get(),
            cabinetDao = get()
        )
    }
}