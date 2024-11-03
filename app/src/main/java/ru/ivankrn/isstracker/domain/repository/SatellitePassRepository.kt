package ru.ivankrn.isstracker.domain.repository

import java.math.BigDecimal

import ru.ivankrn.isstracker.domain.model.SatellitePassWithEvents

interface SatellitePassRepository {

    suspend fun getPassesForNoradId(
        noradId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        limit: Int = 1,
        days: Int = 1,
        onlyVisible: Boolean = false
    ): List<SatellitePassWithEvents>

    suspend fun getAllFavorites(): List<SatellitePassWithEvents>

    suspend fun addToFavorites(satellitePassWithEvents: SatellitePassWithEvents)

    suspend fun removeFromFavorites(satellitePassWithEvents: SatellitePassWithEvents)

}