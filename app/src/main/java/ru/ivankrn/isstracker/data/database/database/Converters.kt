package ru.ivankrn.isstracker.data.database.database

import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromBigDecimal(value: BigDecimal): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }

    @TypeConverter
    fun fromZonedDateTime(value: ZonedDateTime): Long {
        return value.toInstant().toEpochMilli()
    }

    @TypeConverter
    fun longToZonedDateTime(value: Long): ZonedDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.of("UTC"))
    }

}