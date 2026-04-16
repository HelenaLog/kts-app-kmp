package com.github.helenalog.ktsappkmp.core.domain.workspace.usecase

import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository

class SelectCabinetUseCase(
    private val repository: WorkspaceRepository
) {
    suspend operator fun invoke(cabinetId: String): Result<Unit> =
        repository.selectCabinet(cabinetId)
}
