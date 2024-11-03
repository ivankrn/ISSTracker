package ru.ivankrn.isstracker.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

import ru.ivankrn.isstracker.data.database.dao.AstronomyEventDao
import ru.ivankrn.isstracker.data.database.dao.SatellitePassDao
import ru.ivankrn.isstracker.data.database.database.SatellitePassDatabase
import ru.ivankrn.isstracker.data.mapper.ResponseToEntityMapper
import ru.ivankrn.isstracker.data.repository.SettingsRepository
import ru.ivankrn.isstracker.data.repository.SatellitePassRepositoryImpl
import ru.ivankrn.isstracker.data.repository.SharedPreferencesManager
import ru.ivankrn.isstracker.data.util.BadgeContextCache
import ru.ivankrn.isstracker.domain.mapper.EntityMapper
import ru.ivankrn.isstracker.domain.repository.SatellitePassRepository
import ru.ivankrn.isstracker.viewModel.SatellitePassViewModel

val issTrackerAppModule = module {
    single<SatellitePassDatabase> {
        Room.databaseBuilder(
            androidContext(),
            SatellitePassDatabase::class.java,
            "satellite_pass_data_database"
        ).build()
    }
    single<SatellitePassDao> {
        val database = get<SatellitePassDatabase>()
        database.getSatellitePassDao()
    }
    single<AstronomyEventDao> {
        val database = get<SatellitePassDatabase>()
        database.getAstronomyEventDao()
    }
    factory { ResponseToEntityMapper() }
    factory { EntityMapper() }
    single<SatellitePassRepository> { SatellitePassRepositoryImpl(get(), get(), get(), get(), get()) }
    single<SharedPreferencesManager> { SharedPreferencesManager(androidContext()) }
    single<SettingsRepository> { SettingsRepository(get()) }
    single<BadgeContextCache> { BadgeContextCache(get()) }
    viewModel { SatellitePassViewModel(get(), get()) }
}