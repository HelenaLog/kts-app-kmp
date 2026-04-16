package com.github.helenalog.ktsappkmp.core.presentation.workspace.model

data class WorkspaceSelectionUi(
    val activeLabel: String,
    val cabinets: List<CabinetUi>,
    val projects: List<ProjectUi>
)
