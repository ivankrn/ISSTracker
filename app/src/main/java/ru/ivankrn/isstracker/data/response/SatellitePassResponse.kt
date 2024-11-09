package ru.ivankrn.isstracker.data.response

import com.google.gson.annotations.SerializedName

data class SatellitePassResponse(
    @SerializedName("norad_id")
    val noradId: Int,
    @SerializedName("visible")
    val isVisible: Boolean,
    @SerializedName("rise")
    val riseEvent: AstronomyEventResponse,
    @SerializedName("culmination")
    val culminationEvent: AstronomyEventResponse,
    @SerializedName("set")
    val setEvent: AstronomyEventResponse
)
