package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.workspace.repository.WorkspaceBootstrapper
import com.github.helenalog.ktsappkmp.core.data.workspace.repository.WorkspaceRepositoryImpl
import com.github.helenalog.ktsappkmp.core.domain.workspace.repository.WorkspaceRepository
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.ObserveActiveWorkspaceUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.ObserveCabinetsUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.ObserveProjectsUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.RefreshWorkspacesUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.SelectCabinetUseCase
import com.github.helenalog.ktsappkmp.core.domain.workspace.usecase.SelectProjectUseCase
import com.github.helenalog.ktsappkmp.core.presentation.workspace.WorkspaceSelectorViewModel
import com.github.helenalog.ktsappkmp.core.presentation.workspace.mapper.WorkspaceUiMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val workspaceModule = module {
    singleOf(::WorkspaceRepositoryImpl) bind WorkspaceRepository::class
    factoryOf(::WorkspaceBootstrapper)
    factoryOf(::ObserveCabinetsUseCase)
    factoryOf(::ObserveProjectsUseCase)
    factoryOf(::ObserveActiveWorkspaceUseCase)
    factoryOf(::SelectCabinetUseCase)
    factoryOf(::SelectProjectUseCase)
    factoryOf(::RefreshWorkspacesUseCase)
    factoryOf(::WorkspaceUiMapper)
    viewModelOf(::WorkspaceSelectorViewModel)
}
