package com.github.helenalog.ktsappkmp.presentation.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class UserAvatarUi(
    val initials: String,
    val photoUrl: String?
) {
    val showPhoto: Boolean get() = photoUrl != null
}