package com.github.helenalog.ktsappkmp.feature.filter.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.filter.data.remote.api.UserListApi
import com.github.helenalog.ktsappkmp.feature.filter.data.repository.FilterRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.filter.domain.repository.FilterRepository
import com.github.helenalog.ktsappkmp.feature.filter.domain.usecase.GetUserListUseCase
import com.github.helenalog.ktsappkmp.feature.filter.presentation.FilterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val filterModule = module {
    factory { UserListApi(get(NetworkQualifier.MAIN)) }
    factory<FilterRepository> { FilterRepositoryImpl(get()) }
    factory { GetUserListUseCase(get()) }
    viewModel { FilterViewModel(get()) }
}
