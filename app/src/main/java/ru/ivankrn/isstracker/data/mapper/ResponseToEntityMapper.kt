package ru.ivankrn.isstracker.data.mapper

import java.math.BigDecimal
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import ru.ivankrn.isstracker.data.response.SatellitePassResponse
import ru.ivankrn.isstracker.domain.model.*

class ResponseToEntityMapper {

    private val dateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX")
        .withZone(ZoneOffset.UTC)
    
    fun convert(response: SatellitePassResponse, latitude: BigDecimal, longitude: BigDecimal): SatellitePassWithEvents {
        val satellitePass = SatellitePass(
            SatellitePass.Pk(
                response.noradId,
                latitude,
                longitude,
                ZonedDateTime.parse(response.riseEvent.utcDatetime, dateTimeFormatter),
            ),
            isVisible = response.isVisible,
            isFavorite = false
        )
        return SatellitePassWithEvents(
            satellitePass,
            AstronomyEvent(
                satellitePass.surrogateId,
                AstronomyEvent.Type.RISE,
                response.riseEvent.altitude,
                response.riseEvent.azimuth,
                response.riseEvent.azimuthOctant,
                ZonedDateTime.parse(response.riseEvent.utcDatetime, dateTimeFormatter),
                response.riseEvent.isSunlit,
                response.riseEvent.isVisible
            ),
            AstronomyEvent(
                satellitePass.surrogateId,
                AstronomyEvent.Type.CULMINATION,
                response.culminationEvent.altitude,
                response.culminationEvent.azimuth,
                response.culminationEvent.azimuthOctant,
                ZonedDateTime.parse(response.culminationEvent.utcDatetime, dateTimeFormatter),
                response.culminationEvent.isSunlit,
                response.culminationEvent.isVisible
            ),
            AstronomyEvent(
                satellitePass.surrogateId,
                AstronomyEvent.Type.SET,
                response.setEvent.altitude,
                response.setEvent.azimuth,
                response.setEvent.azimuthOctant,
                ZonedDateTime.parse(response.setEvent.utcDatetime, dateTimeFormatter),
                response.setEvent.isSunlit,
                response.setEvent.isVisible
            )
        )
    }

}