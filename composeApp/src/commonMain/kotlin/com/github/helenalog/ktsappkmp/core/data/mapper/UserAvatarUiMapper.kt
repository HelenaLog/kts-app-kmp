package com.github.helenalog.ktsappkmp.data.mapper

import com.github.helenalog.ktsappkmp.presentation.ui.models.UserAvatarUi

class UserAvatarUiMapper {
    fun map(name: String, photoUrl: String?): UserAvatarUi {
        val initials = name
            .trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .mapNotNull { it.firstOrNull()?.uppercaseChar() }
            .joinToString("")
            .ifBlank { "?" }
        val hasPhoto = !photoUrl.isNullOrBlank()

        return UserAvatarUi(
            initials = initials,
            photoUrl = photoUrl.takeIf { !it.isNullOrBlank() }
        )
    }
}