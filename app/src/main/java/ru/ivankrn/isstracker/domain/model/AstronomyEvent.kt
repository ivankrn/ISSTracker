package ru.ivankrn.isstracker.domain.model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class AstronomyEvent(
    val altitude: BigDecimal,
    val azimuth: BigDecimal,
    val azimuthOctant: AzimuthOctant,
    val utcDatetime: ZonedDateTime,
    val isSunlit: Boolean,
    val isVisible: Boolean
)
