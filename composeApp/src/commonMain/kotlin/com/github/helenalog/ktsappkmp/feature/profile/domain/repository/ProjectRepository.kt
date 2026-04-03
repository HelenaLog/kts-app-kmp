package com.github.helenalog.ktsappkmp.feature.profile.domain.repository

import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Project

interface ProjectRepository {
    suspend fun getProjects(): Result<List<Project>>
}
