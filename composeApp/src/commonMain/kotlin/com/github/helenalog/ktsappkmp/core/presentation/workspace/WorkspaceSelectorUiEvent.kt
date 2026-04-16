package com.github.helenalog.ktsappkmp.core.presentation.workspace

sealed interface WorkspaceSelectorUiEvent {
    data class SelectCabinet(val cabinetId: String) : WorkspaceSelectorUiEvent
    data class SelectProject(val projectId: String) : WorkspaceSelectorUiEvent
}
