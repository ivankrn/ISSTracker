package ru.ivankrn.isstracker.data.database.dao

import androidx.room.*

import ru.ivankrn.isstracker.domain.model.SatellitePass
import java.math.BigDecimal
import java.time.ZonedDateTime

@Dao
interface SatellitePassDao {

    @Transaction
    @Query("SELECT * FROM satellite_passes WHERE favorite = 1 ORDER BY satellite_pass_datetime")
    suspend fun getAllFavorites(): List<SatellitePass>

    @Query("SELECT EXISTS (" +
            "SELECT * FROM satellite_passes " +
            "WHERE norad_id = :noradId " +
            "   AND latitude = :latitude AND longitude = :longitude AND satellite_pass_datetime = :dateTime" +
            ")")
    suspend fun existsById(
        noradId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        dateTime: ZonedDateTime
    ): Boolean

    @Insert
    suspend fun insertAll(vararg satellitePasses: SatellitePass)

    @Update
    suspend fun update(satellitePass: SatellitePass)

    @Delete
    suspend fun delete(satellitePass: SatellitePass)

}