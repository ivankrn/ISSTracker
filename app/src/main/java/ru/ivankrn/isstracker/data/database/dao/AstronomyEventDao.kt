package ru.ivankrn.isstracker.data.database.dao

import java.util.*

import androidx.room.*

import ru.ivankrn.isstracker.domain.model.AstronomyEvent

@Dao
interface AstronomyEventDao {

    @Query("SELECT * FROM astronomy_events WHERE satellite_pass_id IN (:satellitePassesIds)")
    suspend fun getAllBySatellitePassIds(satellitePassesIds: Set<UUID>): List<AstronomyEvent>

    @Insert
    suspend fun insertAll(vararg astronomyEvents: AstronomyEvent)

    @Update
    suspend fun update(astronomyEvent: AstronomyEvent)

    @Delete
    suspend fun delete(astronomyEvent: AstronomyEvent)

    @Query("DELETE FROM astronomy_events WHERE satellite_pass_id IN (:satellitePassesIds)")
    suspend fun deleteBySatellitePassIds(satellitePassesIds: Set<UUID>)

}