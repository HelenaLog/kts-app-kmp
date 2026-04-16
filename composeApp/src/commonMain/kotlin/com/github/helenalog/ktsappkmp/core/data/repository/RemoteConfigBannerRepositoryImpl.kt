package com.github.helenalog.ktsappkmp.core.data.repository

import com.github.helenalog.ktsappkmp.core.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.core.data.remote.dto.BannerDto
import com.github.helenalog.ktsappkmp.core.data.remote.network.RemoteConfigProvider
import com.github.helenalog.ktsappkmp.core.data.storage.BannerStorage
import com.github.helenalog.ktsappkmp.core.domain.banner.model.AppBanner
import com.github.helenalog.ktsappkmp.core.domain.banner.repository.RemoteConfigBannerRepository
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import kotlinx.serialization.json.Json

class RemoteConfigBannerRepositoryImpl(
    private val remoteConfigProvider: RemoteConfigProvider,
    private val dismissedStorage: BannerStorage,
    private val json: Json
) : RemoteConfigBannerRepository {

    override suspend fun getBanners(): Result<List<AppBanner>> = suspendRunCatching {
        remoteConfigProvider.fetchAndActivate()
        val raw = remoteConfigProvider.getString(REMOTE_KEY)
        if (raw.isBlank()) {
            emptyList()
        } else {
            val dismissed = dismissedStorage.read(DISMISSED_KEY)
            json.decodeFromString<List<BannerDto>>(raw)
                .filter { it.enabled && it.id !in dismissed }
                .map { it.toDomain() }
        }
    }

    override suspend fun dismiss(id: String) {
        dismissedStorage.add(DISMISSED_KEY, id)
    }

    private companion object {
        const val REMOTE_KEY = "app_banners"
        const val DISMISSED_KEY = "dismissed_banner_ids"
    }
}
