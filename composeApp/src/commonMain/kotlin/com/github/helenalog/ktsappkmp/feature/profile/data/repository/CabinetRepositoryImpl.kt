package com.github.helenalog.ktsappkmp.feature.profile.data.repository

import com.github.helenalog.ktsappkmp.feature.profile.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.profile.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Cabinet
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.api.ProfileApi
import kotlin.coroutines.cancellation.CancellationException

class CabinetRepositoryImpl(
    private val api: ProfileApi,
    private val cabinetDao: CabinetDao
) : CabinetRepository {

    override suspend fun getCabinets(): Result<List<Cabinet>> = suspendRunCatching {
        try {
            fetchRemoteCabinets()
        } catch (e: Exception) {
            fetchCachedCabinets(e)
        }
    }

    private suspend fun fetchRemoteCabinets(): List<Cabinet> {
        val cabinetsDto = api.getCabinets().data.cabinets
        val domainCabinets = cabinetsDto.map { it.toDomain() }
        cabinetDao.upsertAll(domainCabinets.map { it.toEntity() })
        return domainCabinets
    }

    private suspend fun fetchCachedCabinets(e: Exception): List<Cabinet> {
        if (e is CancellationException) throw e
        val cached = cabinetDao.getAll()
        if (cached.isEmpty()) throw e
        return cached.map { it.toDomain() }
    }
}