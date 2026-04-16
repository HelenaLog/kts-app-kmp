package com.github.helenalog.ktsappkmp.core.domain.project.usecase

import com.github.helenalog.ktsappkmp.core.domain.project.model.Project
import com.github.helenalog.ktsappkmp.core.domain.project.repository.ProjectRepository

class GetProjectsUseCase(
    private val repository: ProjectRepository
) {
    suspend operator fun invoke(): Result<List<Project>> = repository.getProjects()
}
