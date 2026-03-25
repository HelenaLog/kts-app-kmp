package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.data.local.DatabaseProvider
import com.github.helenalog.ktsappkmp.data.remote.api.SmartbotApi
import com.github.helenalog.ktsappkmp.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.domain.model.Profile
import com.github.helenalog.ktsappkmp.domain.repository.ProfileRepository
import com.github.helenalog.ktsappkmp.utils.suspendRunCatching
import kotlin.coroutines.cancellation.CancellationException
import com.github.helenalog.ktsappkmp.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.data.mapper.toEntity

class ProfileRepositoryImpl : ProfileRepository {
    private val api = SmartbotApi(Networking.httpClient)
    private val dao = DatabaseProvider.instance.profileDao()

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
        dao.upsert(profile.toEntity())
        return profile
    }

    private suspend fun fetchCachedProfile(e: Throwable): Result<Profile> {
        if (e is CancellationException) throw e
        val cached = dao.get() ?: return Result.failure(e)
        return Result.success(cached.toDomain())
    }
}