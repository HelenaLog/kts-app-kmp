package com.github.helenalog.ktsappkmp.presentation.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class ProfileUi(
    val displayName: String,
    val email: String?,
    val avatar: UserAvatarUi,
)