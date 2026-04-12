package com.github.helenalog.ktsappkmp.core.domain.banner.repository

import com.github.helenalog.ktsappkmp.core.domain.banner.model.AppBanner

interface RemoteConfigBannerRepository {
    suspend fun getBanners(): Result<List<AppBanner>>
    suspend fun dismiss(id: String)
}
