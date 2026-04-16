package com.github.helenalog.ktsappkmp.core.presentation.workspace

import com.github.helenalog.ktsappkmp.core.presentation.workspace.model.CabinetUi
import com.github.helenalog.ktsappkmp.core.presentation.workspace.model.ProjectUi

data class WorkspaceSelectorUiState(
    val activeCabinetName: String = "",
    val activeProjectName: String = "",
    val cabinets: List<CabinetUi> = emptyList(),
    val projects: List<ProjectUi> = emptyList(),
    val activeCabinetId: String? = null,
    val isSheetOpen: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
