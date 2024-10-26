package ru.ivankrn.isstracker.data.mapper

import ru.ivankrn.isstracker.data.response.AstronomyEventResponse
import ru.ivankrn.isstracker.data.response.SatellitePassResponse
import ru.ivankrn.isstracker.domain.model.AstronomyEvent
import ru.ivankrn.isstracker.domain.model.SatellitePass

class ResponseToEntityMapper {

    fun convert(response: SatellitePassResponse): SatellitePass {
        return SatellitePass(
            response.noradId,
            response.isVisible,
            convert(response.riseEvent),
            convert(response.culminationEvent),
            convert(response.setEvent)
        )
    }

    fun convert(response: AstronomyEventResponse): AstronomyEvent {
        return AstronomyEvent(
            response.altitude,
            response.azimuth,
            response.azimuthOctant,
            response.utcDatetime,
            response.isSunlit,
            response.isVisible
        )
    }

}