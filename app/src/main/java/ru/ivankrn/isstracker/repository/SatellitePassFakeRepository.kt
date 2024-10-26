package ru.ivankrn.isstracker.repository

import ru.ivankrn.isstracker.model.AstronomyEvent
import ru.ivankrn.isstracker.model.SatellitePass
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.ZonedDateTime
import java.util.*

class SatellitePassFakeRepository : SatellitePassRepository {

    override fun getPassesForNoradId(
        noradId: Int,
        latitude: BigDecimal,
        longitude: BigDecimal,
        limit: Int,
        days: Int,
        onlyVisible: Boolean
    ): List<SatellitePass> {
        return (0L..limit)
            .map { generatePass(noradId, days = it) }
            .take(limit)
            .filter { if (onlyVisible) it.isVisible else true  }
    }

    private fun generatePass(
        noradId: Int,
        days: Long
    ): SatellitePass {
        val random = Random()
        val minAltitude = 10.0
        val maxAltitude = 20.0
        val minAzimuth = 100.0
        val maxAzimuth = 200.0

        return SatellitePass(
            noradId,
            random.nextBoolean(),
            riseEvent = AstronomyEvent(
                BigDecimal.valueOf(
                    (minAltitude + random.nextDouble() * (maxAltitude - minAltitude))
                ).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(
                    (minAzimuth + random.nextDouble() * (maxAzimuth - minAzimuth))
                ).setScale(2, RoundingMode.HALF_UP),
                AstronomyEvent.AzimuthOctant.S,
                ZonedDateTime.now().plusDays(days).withHour(1).withMinute(18),
                isSunlit = random.nextBoolean(),
                isVisible = random.nextBoolean()
            ),
            culminationEvent = AstronomyEvent(
                BigDecimal.valueOf(
                    (minAltitude + random.nextDouble() * (maxAltitude - minAltitude))
                ).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(
                    (minAzimuth + random.nextDouble() * (maxAzimuth - minAzimuth))
                ).setScale(2, RoundingMode.HALF_UP),
                AstronomyEvent.AzimuthOctant.SE,
                ZonedDateTime.now().plusDays(days).withHour(1).withMinute(20),
                isSunlit = random.nextBoolean(),
                isVisible = random.nextBoolean()
            ),
            setEvent = AstronomyEvent(
                BigDecimal.valueOf(
                    (minAltitude + random.nextDouble() * (maxAltitude - minAltitude))
                ).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(
                    (minAzimuth + random.nextDouble() * (maxAzimuth - minAzimuth))
                ).setScale(2, RoundingMode.HALF_UP),
                AstronomyEvent.AzimuthOctant.SE,
                ZonedDateTime.now().plusDays(days).withHour(1).withMinute(22),
                isSunlit = random.nextBoolean(),
                isVisible = random.nextBoolean()
            )
        )
    }
}