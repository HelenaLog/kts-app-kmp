package com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper

import com.github.helenalog.ktsappkmp.core.presentation.ui.model.UserAvatarUi

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

        return UserAvatarUi(
            initials = initials,
            photoUrl = photoUrl.takeIf { !it.isNullOrBlank() }
        )
    }
}