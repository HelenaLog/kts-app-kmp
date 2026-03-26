package com.github.helenalog.ktsappkmp.feature.profile.presentation.mapper

import com.github.helenalog.ktsappkmp.core.data.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile
import com.github.helenalog.ktsappkmp.feature.profile.presentation.model.ProfileUi

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