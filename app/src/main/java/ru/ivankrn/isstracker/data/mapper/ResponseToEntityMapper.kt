package ru.ivankrn.isstracker.data.mapper

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import ru.ivankrn.isstracker.data.response.AstronomyEventResponse
import ru.ivankrn.isstracker.data.response.SatellitePassResponse
import ru.ivankrn.isstracker.domain.model.AstronomyEvent
import ru.ivankrn.isstracker.domain.model.SatellitePass

class ResponseToEntityMapper {

    private val dateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX")
        .withZone(ZoneOffset.UTC)

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
            ZonedDateTime.parse(response.utcDatetime, dateTimeFormatter),
            response.isSunlit,
            response.isVisible
        )
    }

}