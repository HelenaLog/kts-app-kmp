package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.domain.cabinet.model.Cabinet
import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository
import kotlinx.coroutines.flow.Flow

class ObserveCabinetsUseCase(
    private val repository: WorkspaceRepository
) {
    operator fun invoke(): Flow<List<Cabinet>> = repository.observeCabinets()
}
