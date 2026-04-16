package com.github.helenalog.ktsappkmp.core.data.project.mapper

import com.github.helenalog.ktsappkmp.core.data.project.local.entity.ProjectEntity
import com.github.helenalog.ktsappkmp.core.domain.project.model.Project

fun ProjectEntity.toDomain() = Project(
    id = id,
    name = name
)

fun Project.toEntity() = ProjectEntity(
    id = id,
    name = name
)
