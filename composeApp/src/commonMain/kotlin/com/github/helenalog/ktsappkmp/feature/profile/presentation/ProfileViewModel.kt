package com.github.helenalog.ktsappkmp.feature.profile.presentation

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.core.presentation.common.BaseViewModel
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.GetCabinetsUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.GetProfileUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.GetProjectsUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.LogoutUseCase
import com.github.helenalog.ktsappkmp.utils.suspendRunCatching
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfile: GetProfileUseCase,
    private val getCabinets: GetCabinetsUseCase,
    private val getProjects: GetProjectsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val profileMapper: ProfileUiMapper = ProfileUiMapper()
) : BaseViewModel<ProfileUiState, ProfileUiEvent>(ProfileUiState()) {

    init {
        retryProfile()
    }

    fun logout() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, profileError = null) }
            suspendRunCatching { SessionManager.logout() }
                .onSuccess {
                    updateState { copy(isLoading = false, profileError = null) }
                    sendEvent(ProfileUiEvent.NavigateToLogin)
                }
                .onFailure { e ->
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
            getProfile()
                .onSuccess { profile ->
                    updateState {
                        copy(
                            profile = profileMapper.map(profile),
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
            getCabinets()
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
            getProjects()
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