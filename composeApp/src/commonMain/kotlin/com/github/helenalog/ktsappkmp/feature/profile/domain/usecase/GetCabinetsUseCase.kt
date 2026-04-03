package com.github.helenalog.ktsappkmp.feature.profile.domain.usecase

import com.github.helenalog.ktsappkmp.feature.profile.domain.model.Cabinet
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.CabinetRepository

class GetCabinetsUseCase(
    private val repository: CabinetRepository
) {
    suspend operator fun invoke(): Result<List<Cabinet>> = repository.getCabinets()
}
