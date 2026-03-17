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

class ProjectRepositoryImpl: ProjectRepository {
    private val api = SmartbotApi(Networking.httpClient)
    private val dao = DatabaseProvider.instance.projectDao()

    override suspend fun getProject(): Result<Project> = suspendRunCatching {
        try {
            fetchRemoteProject()
        } catch (e: Exception) {
            fetchCachedProject(e)
        }
    }

    private suspend fun fetchRemoteProject(): Project {
        val projectDto = api.getProjects().data.project ?: error("Project not found")
        val project = projectDto.toDomain()
        dao.upsertAll(listOf(project.toEntity()))
        return project
    }

    private suspend fun fetchCachedProject(e: Exception): Project {
        if (e is CancellationException) throw e
        val cached = dao.getAll()
        val first = cached.firstOrNull() ?: throw e
        return first.toDomain()
    }
}