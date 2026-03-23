package com.github.helenalog.ktsappkmp.feature.profile.data.repository

import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.ProfileDao
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.profile.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.profile.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProfileRepository
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.api.ProfileApi
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl(
    private val api: ProfileApi,
    private val profileDao: ProfileDao,
) : ProfileRepository {

    override suspend fun getProfile(): Result<Profile> = suspendRunCatching {
        try {
            fetchRemoteProfile()
        } catch (e: Exception) {
            fetchCachedProfile(e)
        }
    }

    private suspend fun fetchRemoteProfile(): Profile {
        val manager = api.getAuthInfo().data.manager
        val profile = manager.toDomain()
        profileDao.upsert(profile.toEntity())
        return profile
    }

    private suspend fun fetchCachedProfile(e: Exception): Profile {
        if (e is CancellationException) throw e
        val cached = profileDao.get() ?: throw e
        return cached.toDomain()
    }
}