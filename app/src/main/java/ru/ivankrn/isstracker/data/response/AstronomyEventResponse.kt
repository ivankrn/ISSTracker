package ru.ivankrn.isstracker.data.response

import java.math.BigDecimal
import com.google.gson.annotations.SerializedName

import ru.ivankrn.isstracker.domain.model.AzimuthOctant

data class AstronomyEventResponse(
    @SerializedName("alt")
    val altitude: BigDecimal,
    @SerializedName("az")
    val azimuth: BigDecimal,
    @SerializedName("az_octant")
    val azimuthOctant: AzimuthOctant,
    @SerializedName("utc_datetime")
    val utcDatetime: String,
    @SerializedName("is_sunlit")
    val isSunlit: Boolean,
    @SerializedName("is_visible")
    val isVisible: Boolean
)
