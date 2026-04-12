package com.github.helenalog.ktsappkmp.core.domain.cabinet.repository

import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet

interface CabinetRepository {
    suspend fun getCabinets(): Result<List<Cabinet>>
}
