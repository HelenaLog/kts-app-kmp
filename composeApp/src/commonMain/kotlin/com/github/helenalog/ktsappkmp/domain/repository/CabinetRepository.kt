package com.github.helenalog.ktsappkmp.domain.repository

import com.github.helenalog.ktsappkmp.domain.model.Cabinet

interface CabinetRepository {
    suspend fun getCabinets(): Result<List<Cabinet>>
}