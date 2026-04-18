package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.RemoteConfigProvider
import com.github.helenalog.ktsappkmp.core.data.banner.repository.RemoteConfigBannerRepositoryImpl
import com.github.helenalog.ktsappkmp.core.domain.banner.repository.RemoteConfigBannerRepository
import com.github.helenalog.ktsappkmp.core.domain.banner.usecase.DismissBannerUseCase
import com.github.helenalog.ktsappkmp.core.domain.banner.usecase.GetBannersUseCase
import com.github.helenalog.ktsappkmp.core.presentation.banner.BannersViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val bannerModule = module {
    factoryOf(::RemoteConfigProvider)
    factoryOf(::RemoteConfigBannerRepositoryImpl) { bind<RemoteConfigBannerRepository>() }
    factoryOf(::GetBannersUseCase)
    factoryOf(::DismissBannerUseCase)
    viewModelOf(::BannersViewModel)
}
