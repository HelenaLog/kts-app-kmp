package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.storage.DataStoreSettingsStorage
import com.github.helenalog.ktsappkmp.core.data.storage.SessionManager
import com.github.helenalog.ktsappkmp.core.data.storage.SettingsStorage
import com.github.helenalog.ktsappkmp.data.storage.DataStoreProfileStorage
import com.github.helenalog.ktsappkmp.data.storage.ProfileStorage
import org.koin.dsl.module

val storageModule = module {
    single<SettingsStorage> { DataStoreSettingsStorage(get()) }
    single<ProfileStorage> { DataStoreProfileStorage(get()) }
    single {
        SessionManager(
            sessionStorage = get(),
            profileStorage = get(),
            conversationDao = get(),
            projectDao = get(),
            cabinetDao = get()
        )
    }
}