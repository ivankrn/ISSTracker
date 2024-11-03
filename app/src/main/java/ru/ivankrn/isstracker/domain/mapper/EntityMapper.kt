package ru.ivankrn.isstracker.domain.mapper

import ru.ivankrn.isstracker.domain.model.*

class EntityMapper {

    fun convert(
        satellitePasses: Collection<SatellitePass>,
        astronomyEvents: Collection<AstronomyEvent>
    ): List<SatellitePassWithEvents> {
        val astronomyEventsMap = astronomyEvents
            .groupBy { it.satellitePassId }
            .mapValues { eventsById ->
                eventsById.value
                    .groupBy { event -> event.type }
                    .mapValues { eventsByType -> eventsByType.value.first() }
            }

        return satellitePasses
            .map {
                SatellitePassWithEvents(
                    it,
                    astronomyEventsMap[it.surrogateId]!![AstronomyEvent.Type.RISE]!!,
                    astronomyEventsMap[it.surrogateId]!![AstronomyEvent.Type.CULMINATION]!!,
                    astronomyEventsMap[it.surrogateId]!![AstronomyEvent.Type.SET]!!
                )
            }


    }

}