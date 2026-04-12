package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.cabinet.remote.api.CabinetApi
import com.github.helenalog.ktsappkmp.core.data.cabinet.repository.CabinetRepositoryImpl
import com.github.helenalog.ktsappkmp.core.data.remote.network.NetworkQualifier
import com.github.helenalog.ktsappkmp.core.domain.cabinet.repository.CabinetRepository
import com.github.helenalog.ktsappkmp.core.domain.cabinet.usecase.GetCabinetsUseCase
import org.koin.dsl.module

val cabinetModule = module {
    factory { CabinetApi(get(NetworkQualifier.MAIN)) }
    factory<CabinetRepository> { CabinetRepositoryImpl(api = get(), cabinetDao = get()) }
    factory { GetCabinetsUseCase(get()) }
}
