package com.github.helenalog.ktsappkmp.feature.profile.data.remote.response

import com.github.helenalog.ktsappkmp.feature.profile.data.remote.dto.ProjectDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse(
    @SerialName("project")
    val project: ProjectDto? = null,
    @SerialName("projects")
    val projects: List<ProjectDto>? = null
)
