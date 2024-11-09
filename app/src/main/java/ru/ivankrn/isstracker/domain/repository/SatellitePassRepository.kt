package ru.ivankrn.isstracker.domain.repository

import java.math.BigDecimal

import ru.ivankrn.isstracker.domain.model.SatellitePass

interface SatellitePassRepository {

    suspend fun getPassesForNoradId(
        noradId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        limit: Int = 1,
        days: Int = 1,
        onlyVisible: Boolean = false
    ): List<SatellitePass>

}