package ru.ivankrn.isstracker.repository

import ru.ivankrn.isstracker.model.SatellitePass
import java.math.BigDecimal

interface SatellitePassRepository {

    fun getPassesForNoradId(noradId: Int, latitude: BigDecimal, longitude: BigDecimal,
                            limit: Int = 1, days: Int = 1, onlyVisible: Boolean = false): List<SatellitePass>

}