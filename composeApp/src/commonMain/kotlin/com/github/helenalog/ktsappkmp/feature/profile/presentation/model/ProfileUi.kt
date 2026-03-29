package com.github.helenalog.ktsappkmp.feature.profile.presentation.model

import androidx.compose.runtime.Immutable
import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi

@Immutable
data class ProfileUi(
    val displayName: String,
    val email: String?,
    val avatar: UserAvatarUi,
)
