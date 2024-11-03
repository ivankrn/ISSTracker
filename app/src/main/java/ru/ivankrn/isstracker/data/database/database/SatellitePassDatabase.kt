package ru.ivankrn.isstracker.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import ru.ivankrn.isstracker.data.database.dao.AstronomyEventDao
import ru.ivankrn.isstracker.data.database.dao.SatellitePassDao
import ru.ivankrn.isstracker.domain.model.*

@Database(
    entities = [
        SatellitePass::class,
        AstronomyEvent::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SatellitePassDatabase : RoomDatabase() {

    abstract fun getSatellitePassDao(): SatellitePassDao
    abstract fun getAstronomyEventDao(): AstronomyEventDao

}