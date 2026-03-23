package com.github.helenalog.ktsappkmp.feature.profile.presentation

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.presentation.ui.models.ProfileUi
import com.github.helenalog.ktsappkmp.presentation.ui.models.UserAvatarUi

@Immutable
data class ProfileUiState(
    val profile: ProfileUi = ProfileUi(
        displayName = "",
        email = null,
        avatar = UserAvatarUi(initials = "?", photoUrl = null)
    ),
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