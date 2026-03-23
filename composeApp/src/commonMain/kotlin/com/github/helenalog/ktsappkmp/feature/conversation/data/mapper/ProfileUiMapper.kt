package com.github.helenalog.ktsappkmp.data.mapper

import com.github.helenalog.ktsappkmp.domain.model.Profile
import com.github.helenalog.ktsappkmp.presentation.ui.models.ProfileUi

class ProfileUiMapper(
    private val avatarMapper: UserAvatarUiMapper = UserAvatarUiMapper()
) {
    fun map(profile: Profile): ProfileUi {
        return ProfileUi(
            displayName = profile.name,
            email = profile.email,
            avatar = avatarMapper.map(profile.name, profile.avatarUrl),
        )
    }
}