package com.github.helenalog.ktsappkmp.feature.profile.domain.repository

import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>
}