package com.github.helenalog.ktsappkmp.core.domain.project.repository

import com.github.helenalog.ktsappkmp.core.domain.project.model.Project

interface ProjectRepository {
    suspend fun getProjects(): Result<List<Project>>
}
