package com.github.helenalog.ktsappkmp.core.presentation.workspace.mapper

import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet
import com.github.helenalog.ktsappkmp.core.domain.project.model.Project
import com.github.helenalog.ktsappkmp.core.presentation.workspace.model.CabinetUi
import com.github.helenalog.ktsappkmp.core.presentation.workspace.model.ProjectUi

class WorkspaceUiMapper {

    fun mapCabinets(cabinets: List<Cabinet>, activeCabinetId: String?): List<CabinetUi> =
        cabinets.map { cabinet ->
            CabinetUi(
                id = cabinet.id,
                name = cabinet.name,
                isSelected = cabinet.id == activeCabinetId
            )
        }

    fun mapProjects(projects: List<Project>, activeProjectId: String?): List<ProjectUi> =
        projects.map { project ->
            ProjectUi(
                id = project.id,
                name = project.name,
                isSelected = project.id == activeProjectId
            )
        }
}
