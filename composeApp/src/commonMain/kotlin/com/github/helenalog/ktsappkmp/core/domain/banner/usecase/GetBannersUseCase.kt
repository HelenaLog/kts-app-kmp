package com.github.helenalog.ktsappkmp.core.domain.banner.usecase

import com.github.helenalog.ktsappkmp.core.domain.banner.model.AppBanner
import com.github.helenalog.ktsappkmp.core.domain.banner.repository.RemoteConfigBannerRepository

class GetBannersUseCase(
    private val repository: RemoteConfigBannerRepository
) {
    suspend operator fun invoke(): Result<List<AppBanner>> =
        repository.getBanners()
}
