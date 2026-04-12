package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.storage.BannerStorage
import com.github.helenalog.ktsappkmp.core.data.storage.DataStoreBannerStorage
import com.github.helenalog.ktsappkmp.core.data.storage.DataStoreProfileStorage
import com.github.helenalog.ktsappkmp.core.data.storage.DataStoreSettingsStorage
import com.github.helenalog.ktsappkmp.core.data.storage.ProfileStorage
import com.github.helenalog.ktsappkmp.core.data.storage.SessionManager
import com.github.helenalog.ktsappkmp.core.data.storage.SettingsStorage
import org.koin.dsl.module

val storageModule = module {
    single<SettingsStorage> { DataStoreSettingsStorage(get()) }
    single<ProfileStorage> { DataStoreProfileStorage(get()) }
    single<BannerStorage> { DataStoreBannerStorage(get(), get()) }
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
