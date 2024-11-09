package ru.ivankrn.isstracker.data.repository

import java.math.BigDecimal

import ru.ivankrn.isstracker.util.AppConstants.DEFAULT_LATITUDE
import ru.ivankrn.isstracker.util.AppConstants.DEFAULT_LONGITUDE

class SettingsRepository(private val sharedPreferencesManager: SharedPreferencesManager) {

    private val LATITUDE_KEY = "saved_latitude"
    private val LONGITUDE_KEY = "saved_longitude"

    fun getLatitude(): BigDecimal {
        return sharedPreferencesManager.getBigDecimal(
            LATITUDE_KEY,
            BigDecimal.valueOf(DEFAULT_LATITUDE)
        )
    }

    fun getLongitude(): BigDecimal {
        return sharedPreferencesManager.getBigDecimal(
            LONGITUDE_KEY,
            BigDecimal.valueOf(DEFAULT_LONGITUDE)
        )
    }

    fun saveLatitude(latitude: BigDecimal) {
        sharedPreferencesManager.saveBigDecimal(
            LATITUDE_KEY,
            latitude
        )
    }

    fun saveLongitude(longitude: BigDecimal) {
        sharedPreferencesManager.saveBigDecimal(
            LONGITUDE_KEY,
            longitude
        )
    }

}