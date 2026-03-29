package com.github.helenalog.ktsappkmp.feature.profile.data.repository

import com.github.helenalog.ktsappkmp.core.data.storage.ProfileStorage
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.profile.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProfileRepository
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.api.ProfileApi
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl(
    private val api: ProfileApi,
    private val profileStorage: ProfileStorage
) : ProfileRepository {

    override suspend fun getProfile(): Result<Profile> {
        return suspendRunCatching { fetchRemoteProfile() }
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { fetchCachedProfile(it) }
            )
    }

    private suspend fun fetchRemoteProfile(): Profile {
        val manager = api.getAuthInfo().data.manager
        val profile = manager.toDomain()
        profileStorage.save(profile)
        return profile
    }

    private suspend fun fetchCachedProfile(e: Throwable): Result<Profile> {
        if (e is CancellationException) throw e
        val cached = profileStorage.getProfile() ?: return Result.failure(e)
        return Result.success(cached)
    }
}
