package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.project.remote.api.ProjectApi
import com.github.helenalog.ktsappkmp.core.data.project.repository.ProjectRepositoryImpl
import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.core.domain.project.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.core.domain.project.usecase.GetProjectsUseCase
import org.koin.dsl.module

val projectModule = module {
    factory { ProjectApi(get(NetworkQualifier.MAIN)) }
    factory<ProjectRepository> { ProjectRepositoryImpl(api = get(), projectDao = get()) }
    factory { GetProjectsUseCase(get()) }
}
