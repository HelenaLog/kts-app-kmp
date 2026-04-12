package com.github.helenalog.ktsappkmp.core.data.cabinet.repository

import com.github.helenalog.ktsappkmp.core.data.cabinet.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.core.data.cabinet.mapper.toDomain
import com.github.helenalog.ktsappkmp.core.data.cabinet.mapper.toEntity
import com.github.helenalog.ktsappkmp.core.data.cabinet.remote.api.CabinetApi
import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet
import com.github.helenalog.ktsappkmp.core.domain.cabinet.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import kotlin.coroutines.cancellation.CancellationException

class CabinetRepositoryImpl(
    private val api: CabinetApi,
    private val cabinetDao: CabinetDao
) : CabinetRepository {

    override suspend fun getCabinets(): Result<List<Cabinet>> {
        return suspendRunCatching { fetchRemoteCabinets() }
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { fetchCachedCabinets(it) }
            )
    }

    private suspend fun fetchRemoteCabinets(): List<Cabinet> {
        val cabinetsDto = api.getCabinets().data.cabinets
        val domainCabinets = cabinetsDto.map { it.toDomain() }
        cabinetDao.upsertAll(domainCabinets.map { it.toEntity() })
        return domainCabinets
    }

    private suspend fun fetchCachedCabinets(e: Throwable): Result<List<Cabinet>> {
        if (e is CancellationException) throw e
        val cached = cabinetDao.getAll()
        if (cached.isEmpty()) return Result.failure(e)
        return Result.success(cached.map { it.toDomain() })
    }
}
