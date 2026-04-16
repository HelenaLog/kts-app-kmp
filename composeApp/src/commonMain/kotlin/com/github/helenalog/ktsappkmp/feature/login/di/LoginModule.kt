package com.github.helenalog.ktsappkmp.feature.login.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.login.data.remote.api.LoginApi
import com.github.helenalog.ktsappkmp.feature.login.data.repository.LoginRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.login.domain.repository.LoginRepository
import com.github.helenalog.ktsappkmp.feature.login.domain.usecase.LoginUseCase
import com.github.helenalog.ktsappkmp.feature.login.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    factory { LoginApi(get(NetworkQualifier.AUTH)) }
    factory<LoginRepository> { LoginRepositoryImpl(api = get(), sessionStorage = get()) }
    factory { LoginUseCase(get()) }

    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            workspaceBootstrapper = get()
        )
    }
}
