package com.github.helenalog.ktsappkmp.presentation.screens.profile

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.data.repository.CabinetRepositoryImpl
import com.github.helenalog.ktsappkmp.data.repository.ProfileRepositoryImpl
import com.github.helenalog.ktsappkmp.data.repository.ProjectRepositoryImpl
import com.github.helenalog.ktsappkmp.data.storage.SessionManager
import com.github.helenalog.ktsappkmp.domain.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.domain.repository.ProfileRepository
import com.github.helenalog.ktsappkmp.domain.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val repository: ProfileRepository = ProfileRepositoryImpl(),
    private val cabinetRepository: CabinetRepository = CabinetRepositoryImpl(),
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) : BaseViewModel<ProfileUiState, ProfileUiEvent>(ProfileUiState()) {

    init {
        retryProfile()
    }

    fun logout() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, profileError = null) }
            try {
                SessionManager.logout()
                sendEvent(ProfileUiEvent.NavigateToLogin)
            } catch (e: Exception) {
                updateState { copy(isLoading = false, profileError = e.message) }
            }
        }
    }

    fun retryProfile() {
        loadProfile()
        loadCabinets()
        loadProjects()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            updateState { copy(isProfileLoading = true, profileError = null) }
            repository.getProfile()
                .onSuccess { profile ->
                    updateState {
                        copy(
                            name = profile.name,
                            email = profile.email,
                            avatarUrl = profile.avatarUrl,
                            isProfileLoading = false,
                            profileError = null
                        )
                    }
                }
                .onFailure { e ->
                    updateState {
                        copy(
                            isProfileLoading = false,
                            profileError = e.message ?: ERROR_PROFILE_LOAD
                        )
                    }
                }
        }
    }

    private fun loadCabinets() {
        viewModelScope.launch {
            updateState {
                copy(
                    isCabinetsLoading = true,
                    cabinetsError = null,
                )
            }
            cabinetRepository.getCabinets()
                .onSuccess { cabinets ->
                    updateState {
                        copy(
                            cabinetNames = cabinets.map { it.name },
                            isCabinetsLoading = false,
                            cabinetsError = null
                        )
                    }
                }
                .onFailure { e ->
                    updateState {
                        copy(
                            isCabinetsLoading = false,
                            cabinetsError = e.message ?: ERROR_CABINETS_LOAD,
                        )
                    }
                }
        }
    }

    private fun loadProjects() {
        viewModelScope.launch {
            updateState {
                copy(
                    isProjectsLoading = true,
                    projectsError = null
                )
            }
            projectRepository
                .getProjects()
                .onSuccess { projects ->
                    updateState {
                        copy(
                            projectNames = projects.map { it.name },
                            isProjectsLoading = false,
                            projectsError = null
                        )
                    }
                }
                .onFailure { e ->
                    updateState {
                        copy(
                            isProjectsLoading = false,
                            projectsError = e.message ?: ERROR_PROJECTS_LOAD
                        )
                    }
                }
        }
    }

    companion object {
        private const val ERROR_PROFILE_LOAD = "Failed to load profile"
        private const val ERROR_CABINETS_LOAD = "Failed to load cabinets"
        private const val ERROR_PROJECTS_LOAD = "Failed to load projects"
    }
}