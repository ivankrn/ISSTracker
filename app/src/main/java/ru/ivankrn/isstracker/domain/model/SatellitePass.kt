package ru.ivankrn.isstracker.domain.model

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

import androidx.room.*

@Entity(
    tableName = "satellite_passes",
    indices = [
        Index(value = ["surrogate_id"], unique = true)
    ]
)
data class SatellitePass(
    @Embedded
    @PrimaryKey
    var id: Pk,
    @ColumnInfo(name = "surrogate_id")
    var surrogateId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "visible")
    var isVisible: Boolean,
    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean
) {

    data class Pk(
        @ColumnInfo(name = "norad_id")
        val noradId: Int,
        @ColumnInfo(name = "latitude")
        val latitude: BigDecimal,
        @ColumnInfo(name = "longitude")
        val longitude: BigDecimal,
        @ColumnInfo(name = "satellite_pass_datetime")
        val dateTime: ZonedDateTime
    )

}
