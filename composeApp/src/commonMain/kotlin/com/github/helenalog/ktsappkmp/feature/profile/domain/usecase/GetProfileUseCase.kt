package com.github.helenalog.ktsappkmp.feature.profile.domain.usecase

import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Profile
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Profile> = repository.getProfile()
}
