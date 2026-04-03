package com.github.helenalog.ktsappkmp.feature.profile.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.feature.profile.presentation.mapper.ProfileUiMapper
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.api.ProfileApi
import com.github.helenalog.ktsappkmp.feature.profile.data.repository.CabinetRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.profile.data.repository.ProfileRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.profile.data.repository.ProjectRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProfileRepository
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.GetCabinetsUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.GetProfileUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.GetProjectsUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.LogoutUseCase
import com.github.helenalog.ktsappkmp.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    factory { ProfileApi(get(NetworkQualifier.MAIN)) }

    factory<ProfileRepository> { ProfileRepositoryImpl(api = get(), profileStorage = get()) }
    factory<CabinetRepository> { CabinetRepositoryImpl(api = get(), cabinetDao = get()) }
    factory<ProjectRepository> { ProjectRepositoryImpl(api = get(), projectDao = get()) }

    factory { GetProfileUseCase(get()) }
    factory { GetCabinetsUseCase(get()) }
    factory { GetProjectsUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { UserAvatarUiMapper() }
    factory { ProfileUiMapper(get()) }

    viewModel {
        ProfileViewModel(
            getProfile = get(),
            getCabinets = get(),
            getProjects = get(),
            logoutUseCase = get(),
            profileMapper = get()
        )
    }
}
