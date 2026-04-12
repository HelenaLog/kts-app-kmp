package com.github.helenalog.ktsappkmp.core.domain.banner.usecase

import com.github.helenalog.ktsappkmp.core.domain.banner.repository.RemoteConfigBannerRepository

class DismissBannerUseCase(
    private val repository: RemoteConfigBannerRepository
) {
    suspend operator fun invoke(id: String) {
        repository.dismiss(id)
    }
}
