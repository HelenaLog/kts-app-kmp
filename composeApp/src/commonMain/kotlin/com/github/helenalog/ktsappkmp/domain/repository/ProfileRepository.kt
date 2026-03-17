package com.github.helenalog.ktsappkmp.domain.repository

import com.github.helenalog.ktsappkmp.domain.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>
}