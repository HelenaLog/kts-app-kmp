package com.github.helenalog.ktsappkmp.di

import com.github.helenalog.ktsappkmp.BuildKonfig
import com.github.helenalog.ktsappkmp.data.remote.network.HttpClientFactory
import com.github.helenalog.ktsappkmp.data.remote.network.NetworkConfig
import com.github.helenalog.ktsappkmp.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.data.remote.network.OnUnauthorizedCallback
import com.github.helenalog.ktsappkmp.data.storage.SessionStorage
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {

    single<NetworkConfig> {
        NetworkConfig(
            sproUrl = BuildKonfig.SPRO_URL,
            authUrl = BuildKonfig.BASE_URL,
            cabinetId = BuildKonfig.CABINET_ID,
            projectId = BuildKonfig.PROJECT_ID,
        )
    }

    single<HttpClient>(NetworkQualifier.AUTH) {
        HttpClientFactory.createAuthClient(get())
    }

    single<HttpClient>(NetworkQualifier.MAIN) {
        val config: NetworkConfig = get()
        val session: SessionStorage = get()
        val onUnauthorized: OnUnauthorizedCallback = get()
        HttpClientFactory.createMainClient(
            config = config,
            sessionStorage = session,
            onUnauthorizedCallback = onUnauthorized
        )
    }

    single<OnUnauthorizedCallback> { OnUnauthorizedCallback() }
}