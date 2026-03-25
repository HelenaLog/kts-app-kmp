package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.data.local.DatabaseProvider
import com.github.helenalog.ktsappkmp.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.data.remote.api.SmartbotApi
import com.github.helenalog.ktsappkmp.data.remote.network.Networking
import com.github.helenalog.ktsappkmp.domain.model.Project
import com.github.helenalog.ktsappkmp.domain.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.utils.suspendRunCatching
import kotlin.coroutines.cancellation.CancellationException

class ProjectRepositoryImpl : ProjectRepository {
    private val api = SmartbotApi(Networking.httpClient)
    private val dao = DatabaseProvider.instance.projectDao()

    override suspend fun getProjects(): Result<List<Project>> {
        return suspendRunCatching { fetchRemoteProject() }
            .fold(
                onSuccess = { Result.success(it) },
                onFailure = { fetchCachedProject(it) }
            )
    }

    private suspend fun fetchRemoteProject(): List<Project> {
        val projectDto = api.getProjects().data.projects.orEmpty()
        val domainProjects = projectDto.map { it.toDomain() }
        dao.upsertAll(domainProjects.map { it.toEntity() })
        return domainProjects
    }

    private suspend fun fetchCachedProject(e: Throwable): Result<List<Project>> {
        if (e is CancellationException) throw e
        val cached = dao.getAll()
        if (cached.isEmpty()) return Result.failure(e)
        return Result.success(cached.map { it.toDomain() })
    }
}