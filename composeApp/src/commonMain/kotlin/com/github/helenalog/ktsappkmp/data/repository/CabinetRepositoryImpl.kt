package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.data.local.DatabaseProvider
import com.github.helenalog.ktsappkmp.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.data.remote.api.SmartbotApi
import com.github.helenalog.ktsappkmp.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.domain.model.Cabinet
import com.github.helenalog.ktsappkmp.domain.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.utils.suspendRunCatching
import kotlin.coroutines.cancellation.CancellationException

class CabinetRepositoryImpl : CabinetRepository {
    private val api = SmartbotApi(Networking.httpClient)
    private val dao = DatabaseProvider.instance.cabinetDao()

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
        dao.upsertAll(domainCabinets.map { it.toEntity() })
        return domainCabinets
    }

    private suspend fun fetchCachedCabinets(e: Throwable): Result<List<Cabinet>> {
        if (e is CancellationException) throw e
        val cached = dao.getAll()
        if (cached.isEmpty()) return Result.failure(e)
        return Result.success(cached.map { it.toDomain() })
    }
}