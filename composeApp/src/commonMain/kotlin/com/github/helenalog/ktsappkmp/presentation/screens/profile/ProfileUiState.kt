package com.github.helenalog.ktsappkmp.presentation.screens.profile

import androidx.compose.runtime.Immutable

@Immutable
data class ProfileUiState(
    val name: String = "",
    val email: String? = null,
    val avatarUrl: String? = null,
    val isLoading: Boolean = false,
    val isProfileLoading: Boolean = false,
    val profileError: String? = null,

    val isCabinetsLoading: Boolean = false,
    val cabinetNames: List<String> = emptyList(),
    val cabinetsError: String? = null,

    val isProjectsLoading: Boolean = false,
    val projectNames: List<String> = emptyList(),
    val projectsError: String? = null,
)