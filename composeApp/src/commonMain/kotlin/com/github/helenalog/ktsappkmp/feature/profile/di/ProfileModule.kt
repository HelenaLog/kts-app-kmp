package com.github.helenalog.ktsappkmp.feature.profile.di

import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.feature.conversation.presentation.mapper.UserAvatarUiMapper
import com.github.helenalog.ktsappkmp.feature.profile.presentation.mapper.ProfileUiMapper
import com.github.helenalog.ktsappkmp.feature.profile.data.remote.api.ProfileApi
import com.github.helenalog.ktsappkmp.core.data.cabinet.repository.CabinetRepositoryImpl
import com.github.helenalog.ktsappkmp.feature.profile.data.repository.ProfileRepositoryImpl
import com.github.helenalog.ktsappkmp.core.data.project.repository.ProjectRepositoryImpl
import com.github.helenalog.ktsappkmp.core.domain.cabinet.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.feature.profile.domain.repository.ProfileRepository
import com.github.helenalog.ktsappkmp.core.domain.project.repository.ProjectRepository
import com.github.helenalog.ktsappkmp.core.domain.cabinet.usecase.GetCabinetsUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.GetProfileUseCase
import com.github.helenalog.ktsappkmp.core.domain.project.usecase.GetProjectsUseCase
import com.github.helenalog.ktsappkmp.feature.profile.domain.usecase.LogoutUseCase
import com.github.helenalog.ktsappkmp.feature.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    factory { ProfileApi(get(NetworkQualifier.MAIN)) }

    factory<ProfileRepository> { ProfileRepositoryImpl(api = get(), profileStorage = get()) }

    factory { GetProfileUseCase(get()) }
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
