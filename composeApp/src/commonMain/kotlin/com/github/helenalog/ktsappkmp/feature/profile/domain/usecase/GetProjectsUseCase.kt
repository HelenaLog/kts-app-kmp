package com.github.helenalog.ktsappkmp.feature.profile.domain.usecase

import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Project
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProjectRepository

class GetProjectsUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(): Result<List<Project>> = repository.getProjects()
}
