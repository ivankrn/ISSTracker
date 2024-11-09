package ru.ivankrn.isstracker.data.repository

import java.math.BigDecimal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.ivankrn.isstracker.data.api.SatellitePassesApi
import ru.ivankrn.isstracker.data.mapper.ResponseToEntityMapper
import ru.ivankrn.isstracker.domain.model.SatellitePass
import ru.ivankrn.isstracker.domain.repository.SatellitePassRepository

class SatellitePassWebRepository(
    private val api: SatellitePassesApi,
    private val responseToEntityMapper: ResponseToEntityMapper
) : SatellitePassRepository {

    override suspend fun getPassesForNoradId(
        noradId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        limit: Int,
        days: Int,
        onlyVisible: Boolean
    ): List<SatellitePass> {
        return withContext(Dispatchers.IO) {
            val response = api.getSatellitePassesForNoradId(
                noradId,
                latitude,
                longitude,
                limit,
                days,
                onlyVisible
            )

            response
                .map { responseToEntityMapper.convert(it) }
        }
    }

}