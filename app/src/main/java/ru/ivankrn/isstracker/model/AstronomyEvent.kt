package ru.ivankrn.isstracker.model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class AstronomyEvent(
    val altitude: BigDecimal,
    val az: BigDecimal,
    val azOctant: AzimuthOctant,
    val utcDatetime: ZonedDateTime,
    val isSunlit: Boolean,
    val isVisible: Boolean
) {
    enum class AzimuthOctant {
        N, NE, E, SE, S, SW, W, NW
    }
}
