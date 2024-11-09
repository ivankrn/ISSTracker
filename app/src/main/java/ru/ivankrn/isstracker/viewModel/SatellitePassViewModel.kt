package ru.ivankrn.isstracker.viewModel

import java.math.BigDecimal

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import ru.ivankrn.isstracker.data.repository.SettingsRepository
import ru.ivankrn.isstracker.domain.model.SatellitePassWithEvents
import ru.ivankrn.isstracker.domain.repository.SatellitePassRepository
import ru.ivankrn.isstracker.util.AppConstants.DEFAULT_LATITUDE
import ru.ivankrn.isstracker.util.AppConstants.DEFAULT_LONGITUDE
import ru.ivankrn.isstracker.util.AppConstants.ISS_NORAD_ID
import ru.ivankrn.isstracker.util.launchLoadingAndError
import ru.ivankrn.isstracker.viewModel.state.ListState

class SatellitePassViewModel(
    private val satellitePassRepository: SatellitePassRepository,
    private val settingsRepository: SettingsRepository
): ViewModel() {

    private val mutableState = MutableListState()
    val viewState = mutableState as ListState
    private val _selectedSatellitePass = mutableStateOf<SatellitePassWithEvents?>(null)
    val selectedSatellitePass: State<SatellitePassWithEvents?> = _selectedSatellitePass
    private val _latitude = mutableStateOf<BigDecimal>(BigDecimal.valueOf(DEFAULT_LATITUDE))
    val latitude: State<BigDecimal> = _latitude
    private val _longitude = mutableStateOf<BigDecimal>(BigDecimal.valueOf(DEFAULT_LONGITUDE))
    val longitude: State<BigDecimal> = _longitude

    init {
        fetchSatellitePasses()
        fetchFavoriteSatellitePasses()
    }

    private fun fetchSatellitePasses() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.error = null

            _latitude.value = settingsRepository.getLatitude()
            _longitude.value = settingsRepository.getLongitude()
            mutableState.items = satellitePassRepository.getPassesForNoradId(
                ISS_NORAD_ID,
                latitude.value,
                longitude.value,
                limit = 14
            )
            _selectedSatellitePass.value = mutableState.items.first()
        }
    }

    private fun fetchFavoriteSatellitePasses() {
      viewModelScope.launch {
          mutableState.favoriteItems = satellitePassRepository.getAllFavorites()
      }
    }

    fun updateSelectedSatellitePass(satellitePass: SatellitePassWithEvents) {
        _selectedSatellitePass.value = satellitePass
    }

    fun saveLocation(latitude: BigDecimal, longitude: BigDecimal) {
        settingsRepository.saveLatitude(latitude)
        settingsRepository.saveLongitude(longitude)
        fetchSatellitePasses()
    }

    fun addToFavorites(satellitePass: SatellitePassWithEvents) {
        viewModelScope.launch {
            satellitePassRepository.addToFavorites(satellitePass)
            fetchFavoriteSatellitePasses()
        }
    }

    fun removeFromFavorites(satellitePass: SatellitePassWithEvents) {
        viewModelScope.launch {
            satellitePassRepository.removeFromFavorites(satellitePass)
            fetchFavoriteSatellitePasses()
        }
    }

    private class MutableListState : ListState {
        override var items: List<SatellitePassWithEvents> by mutableStateOf(emptyList())
        override var favoriteItems: List<SatellitePassWithEvents> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }

}