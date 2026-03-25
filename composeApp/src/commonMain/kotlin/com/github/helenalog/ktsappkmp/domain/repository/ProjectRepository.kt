package com.github.helenalog.ktsappkmp.domain.repository

import com.github.helenalog.ktsappkmp.domain.model.Project

interface ProjectRepository {
    suspend fun getProjects(): Result<List<Project>>
}