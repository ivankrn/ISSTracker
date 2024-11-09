package ru.ivankrn.isstracker.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

import ru.ivankrn.isstracker.data.mapper.ResponseToEntityMapper
import ru.ivankrn.isstracker.data.repository.SatellitePassWebRepository
import ru.ivankrn.isstracker.domain.repository.SatellitePassRepository
import ru.ivankrn.isstracker.viewModel.SatellitePassViewModel

val issTrackerAppModule = module {
    factory { ResponseToEntityMapper() }
    single<SatellitePassRepository> { SatellitePassWebRepository(get(), get()) }
    viewModel { SatellitePassViewModel(get()) }
}