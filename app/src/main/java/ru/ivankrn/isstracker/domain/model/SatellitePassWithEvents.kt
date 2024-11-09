package ru.ivankrn.isstracker.domain.model

data class SatellitePassWithEvents(
    var satellitePass: SatellitePass,
    var riseEvent: AstronomyEvent,
    var culminationEvent: AstronomyEvent,
    var setEvent: AstronomyEvent
)
