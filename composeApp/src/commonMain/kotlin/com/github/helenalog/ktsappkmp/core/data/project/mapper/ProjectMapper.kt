package com.github.helenalog.ktsappkmp.core.data.project.mapper

import com.github.helenalog.ktsappkmp.core.data.project.remote.dto.ProjectDto
import com.github.helenalog.ktsappkmp.core.domain.project.model.Project

fun ProjectDto.toDomain() = Project(
    id = id,
    name = name
)
