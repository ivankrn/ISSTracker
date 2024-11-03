package ru.ivankrn.isstracker.domain.model

import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

import androidx.room.*

@Entity(
    tableName = "astronomy_events",
    primaryKeys = ["satellite_pass_id", "type"],
    foreignKeys = [
        ForeignKey(
            entity = SatellitePass::class,
            parentColumns = ["surrogate_id"],
            childColumns = ["satellite_pass_id"]
        )
    ]
)
data class AstronomyEvent(
    @ColumnInfo(name = "satellite_pass_id")
    var satellitePassId: UUID,
    @ColumnInfo(name = "type")
    var type: Type,
    @ColumnInfo(name = "altitude")
    var altitude: BigDecimal,
    @ColumnInfo(name = "azimuth")
    var azimuth: BigDecimal,
    @ColumnInfo(name = "azimuth_octant")
    var azimuthOctant: AzimuthOctant,
    @ColumnInfo(name = "datetime")
    var utcDatetime: ZonedDateTime,
    @ColumnInfo(name = "sunlit")
    var isSunlit: Boolean,
    @ColumnInfo(name = "visible")
    var isVisible: Boolean
) {
    enum class Type {
        RISE, CULMINATION, SET
    }
}
