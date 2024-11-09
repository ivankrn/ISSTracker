package ru.ivankrn.isstracker.data.util

import java.math.BigDecimal

import ru.ivankrn.isstracker.data.repository.SettingsRepository
import ru.ivankrn.isstracker.util.AppConstants.DEFAULT_LATITUDE
import ru.ivankrn.isstracker.util.AppConstants.DEFAULT_LONGITUDE

class BadgeContextCache(private val settingsRepository: SettingsRepository) {

    fun isSettingsModified(): Boolean {
        val isSameLatitude = settingsRepository.getLatitude().compareTo(BigDecimal.valueOf(DEFAULT_LATITUDE)) == 0
        val isSameLongitude = settingsRepository.getLongitude().compareTo(BigDecimal.valueOf(DEFAULT_LONGITUDE)) == 0

        return !(isSameLatitude && isSameLongitude)
    }

}