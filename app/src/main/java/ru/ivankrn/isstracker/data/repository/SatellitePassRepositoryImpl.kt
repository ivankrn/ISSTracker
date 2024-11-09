package ru.ivankrn.isstracker.data.repository

import java.math.BigDecimal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.ivankrn.isstracker.data.api.SatellitePassesApi
import ru.ivankrn.isstracker.data.database.dao.AstronomyEventDao
import ru.ivankrn.isstracker.data.database.dao.SatellitePassDao
import ru.ivankrn.isstracker.data.mapper.ResponseToEntityMapper
import ru.ivankrn.isstracker.domain.mapper.EntityMapper
import ru.ivankrn.isstracker.domain.model.SatellitePass
import ru.ivankrn.isstracker.domain.model.SatellitePassWithEvents
import ru.ivankrn.isstracker.domain.repository.SatellitePassRepository

class SatellitePassRepositoryImpl(
    private val satellitePassDao: SatellitePassDao,
    private val astronomyEventDao: AstronomyEventDao,
    private val api: SatellitePassesApi,
    private val responseToEntityMapper: ResponseToEntityMapper,
    private val entityMapper: EntityMapper
) : SatellitePassRepository {

    override suspend fun getPassesForNoradId(
        noradId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        limit: Int,
        days: Int,
        onlyVisible: Boolean
    ): List<SatellitePassWithEvents> {
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
                .map { responseToEntityMapper.convert(it, latitude, longitude) }
        }
    }

    override suspend fun getAllFavorites(): List<SatellitePassWithEvents> {
        return withContext(Dispatchers.IO) {
            val satellitePasses = satellitePassDao.getAllFavorites()
            val astronomyEvents = astronomyEventDao.getAllBySatellitePassIds(
                satellitePasses
                    .map { it.surrogateId }
                    .toSet()
            )

            return@withContext entityMapper.convert(satellitePasses, astronomyEvents)
        }
    }

    override suspend fun addToFavorites(satellitePassWithEvents: SatellitePassWithEvents) {
        withContext(Dispatchers.IO) {
            if (existsById(satellitePassWithEvents.satellitePass.id)) {
                return@withContext
            }
            satellitePassWithEvents.satellitePass.isFavorite = true
            satellitePassDao.insertAll(satellitePassWithEvents.satellitePass)
            astronomyEventDao.insertAll(
                satellitePassWithEvents.riseEvent,
                satellitePassWithEvents.culminationEvent,
                satellitePassWithEvents.setEvent
            )
        }
    }

    private suspend fun existsById(satellitePassId: SatellitePass.Pk): Boolean {
        return satellitePassDao.existsById(
            satellitePassId.noradId,
            satellitePassId.latitude,
            satellitePassId.longitude,
            satellitePassId.dateTime
        )
    }

    override suspend fun removeFromFavorites(satellitePassWithEvents: SatellitePassWithEvents) {
        satellitePassWithEvents.satellitePass.isFavorite = false
        withContext(Dispatchers.IO) {
            astronomyEventDao.deleteBySatellitePassIds(setOf(satellitePassWithEvents.satellitePass.surrogateId))
            satellitePassDao.delete(satellitePassWithEvents.satellitePass)
        }
    }

}