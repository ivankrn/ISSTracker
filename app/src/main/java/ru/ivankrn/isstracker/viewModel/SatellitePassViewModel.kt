package ru.ivankrn.isstracker.viewModel

import java.math.BigDecimal
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import ru.ivankrn.isstracker.domain.model.SatellitePass
import ru.ivankrn.isstracker.domain.repository.SatellitePassRepository
import ru.ivankrn.isstracker.util.AppConstants.EKATERINBURG_LATITUDE
import ru.ivankrn.isstracker.util.AppConstants.EKATERINBURG_LONGITUDE
import ru.ivankrn.isstracker.util.AppConstants.ISS_NORAD_ID
import ru.ivankrn.isstracker.util.launchLoadingAndError
import ru.ivankrn.isstracker.viewModel.state.ListState

class SatellitePassViewModel(private val satellitePassRepository: SatellitePassRepository): ViewModel() {

    private val mutableState = MutableListState()
    val viewState = mutableState as ListState
    private val _selectedSatellitePass = mutableStateOf<SatellitePass?>(null)
    val selectedSatellitePass: State<SatellitePass?> = _selectedSatellitePass

    init {
        getSatellitePasses()
    }

    private fun getSatellitePasses() {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.loading = it }
        ) {
            mutableState.error = null

            mutableState.items = satellitePassRepository.getPassesForNoradId(
                ISS_NORAD_ID,
                BigDecimal.valueOf(EKATERINBURG_LATITUDE),
                BigDecimal.valueOf(EKATERINBURG_LONGITUDE),
                limit = 14
            )
            _selectedSatellitePass.value = mutableState.items.first()
        }
    }

    fun updateSelectedSatellitePass(satellitePass: SatellitePass) {
        _selectedSatellitePass.value = satellitePass
    }

    private class MutableListState : ListState {
        override var items: List<SatellitePass> by mutableStateOf(emptyList())
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
    }

}