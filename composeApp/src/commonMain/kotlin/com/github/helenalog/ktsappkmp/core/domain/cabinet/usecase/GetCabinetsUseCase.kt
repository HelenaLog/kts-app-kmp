package com.github.helenalog.ktsappkmp.core.domain.cabinet.usecase

import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet
import com.github.helenalog.ktsappkmp.core.domain.cabinet.repository.CabinetRepository

class GetCabinetsUseCase(
    private val repository: CabinetRepository
) {
    suspend operator fun invoke(): Result<List<Cabinet>> = repository.getCabinets()
}
