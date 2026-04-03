package com.github.helenalog.ktsappkmp.data.storage

import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile

interface ProfileStorage {
    suspend fun save(profile: Profile): Result<Unit>
    suspend fun getProfile(): Profile?
    suspend fun clear(): Result<Unit>
}