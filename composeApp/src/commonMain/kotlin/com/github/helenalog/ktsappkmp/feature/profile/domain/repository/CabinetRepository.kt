package com.github.helenalog.ktsappkmp.feature.profile.domain.repository

import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Cabinet

interface CabinetRepository {
    suspend fun getCabinets(): Result<List<Cabinet>>
}
