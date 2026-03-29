package com.github.helenalog.ktsappkmp.feature.profile.data.repository

import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.ProjectDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.conversation.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.profile.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.profile.data.mapper.toEntity
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Project
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.api.ProfileApi
import kotlin.coroutines.cancellation.CancellationException

class ProjectRepositoryImpl(
    private val api: ProfileApi,
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
