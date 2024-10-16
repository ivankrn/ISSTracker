package ru.ivankrn.isstracker.model

data class SatellitePass(
    val noradId: Int,
    val isVisible: Boolean,
    val riseEvent: AstronomyEvent,
    val culminationEvent: AstronomyEvent,
    val setEvent: AstronomyEvent
)
