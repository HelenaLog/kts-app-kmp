package com.github.helenalog.ktsappkmp.core.presentation.workspace

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.ObserveActiveWorkspaceUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.ObserveCabinetsUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.ObserveProjectsUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.RefreshWorkspacesUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.SelectCabinetUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.SelectProjectUseCase
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.core.presentation.workspace.mapper.WorkspaceUiMapper
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class WorkspaceSelectorViewModel(
    private val observeActive: ObserveActiveWorkspaceUseCase,
    private val observeCabinets: ObserveCabinetsUseCase,
    private val observeProjects: ObserveProjectsUseCase,
    private val selectCabinet: SelectCabinetUseCase,
    private val selectProject: SelectProjectUseCase,
    private val refresh: RefreshWorkspacesUseCase,
    private val mapper: WorkspaceUiMapper
) : BaseViewModel<WorkspaceSelectorUiState, Nothing>(WorkspaceSelectorUiState()) {

    init {
        observeData()
        loadInitial()
    }

    private fun observeData() {
        combine(
            observeActive(),
            observeCabinets(),
            observeProjects()
        ) { active, cabinets, projects ->
            updateState {
                copy(
                    activeLabel = mapper.mapActiveLabel(active),
                    activeCabinetId = active?.cabinet?.id,
                    cabinets = mapper.mapCabinets(cabinets, active?.cabinet?.id),
                    projects = mapper.mapProjects(projects, active?.project?.id)
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun loadInitial() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            refresh()
                .onSuccess { updateState { copy(isLoading = false) } }
                .onFailure { e ->
                    updateState { copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun onEvent(event: WorkspaceSelectorUiEvent) {
        when (event) {
            is WorkspaceSelectorUiEvent.SelectCabinet -> onSelectCabinet(event.cabinetId)
            is WorkspaceSelectorUiEvent.SelectProject -> onSelectProject(event.projectId)
        }
    }

    private fun onSelectCabinet(cabinetId: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            selectCabinet(cabinetId)
                .onSuccess { updateState { copy(isLoading = false) } }
                .onFailure { e -> updateState { copy(isLoading = false, error = e.message) } }
        }
    }

    private fun onSelectProject(projectId: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            selectProject(projectId)
                .onSuccess { updateState { copy(isLoading = false) } }
                .onFailure { e -> updateState { copy(isLoading = false, error = e.message) } }
        }
    }
}
