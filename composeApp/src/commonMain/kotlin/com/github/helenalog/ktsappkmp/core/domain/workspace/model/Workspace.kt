package com.github.helenalog.ktsappkmp.core.domain.workspace.model

import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet
import com.github.helenalog.ktsappkmp.core.domain.project.model.Project

data class Workspace(
    val cabinet: Cabinet,
    val project: Project
)
