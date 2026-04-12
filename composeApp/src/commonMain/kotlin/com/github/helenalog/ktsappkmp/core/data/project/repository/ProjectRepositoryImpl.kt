package com.github.helenalog.ktsappkmp.core.data.project.repository

import com.github.helenalog.ktsappkmp.core.data.project.local.dao.ProjectDao
import com.github.helenalog.ktsappkmp.core.data.project.mapper.toDomain
import com.github.helenalog.ktsappkmp.core.data.project.mapper.toEntity
import com.github.helenalog.ktsappkmp.core.data.project.remote.api.ProjectApi
import com.github.helenalog.ktsappkmp.core.domain.project.model.Project
import com.github.helenalog.ktsappkmp.core.domain.project.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import kotlin.coroutines.cancellation.CancellationException

class ProjectRepositoryImpl(
    private val api: ProjectApi,
    private val projectDao: ProjectDao,
) : ProjectRepository {

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
        projectDao.upsertAll(domainProjects.map { it.toEntity() })
        return domainProjects
    }

    private suspend fun fetchCachedProject(e: Throwable): Result<List<Project>> {
        if (e is CancellationException) throw e
        val cached = projectDao.getAll()
        if (cached.isEmpty()) return Result.failure(e)
        return Result.success(cached.map { it.toDomain() })
    }
}
