package ru.ivankrn.isstracker.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.ivankrn.isstracker.model.SatellitePass
import ru.ivankrn.isstracker.repository.SatellitePassRepository
import ru.ivankrn.isstracker.util.AppConstants.EKATERINBURG_LATITUDE
import ru.ivankrn.isstracker.util.AppConstants.EKATERINBURG_LONGITUDE
import ru.ivankrn.isstracker.util.AppConstants.ISS_NORAD_ID
import java.math.BigDecimal

class SatellitePassViewModel(private val satellitePassRepository: SatellitePassRepository): ViewModel() {

    private val _satellitePasses = mutableStateOf<List<SatellitePass>>(emptyList())
    private val _selectedSatellitePass = mutableStateOf<SatellitePass?>(null)
    val satellitePasses: State<List<SatellitePass>> = _satellitePasses
    val selectedSatellitePass: State<SatellitePass?> = _selectedSatellitePass

    fun getSatellitePasses() {
        _satellitePasses.value = satellitePassRepository.getPassesForNoradId(
            ISS_NORAD_ID,
            BigDecimal.valueOf(EKATERINBURG_LATITUDE),
            BigDecimal.valueOf(EKATERINBURG_LONGITUDE),
            limit = 14
        )
        _selectedSatellitePass.value = _satellitePasses.value.first()
    }

    fun updateSelectedSatellitePass(satellitePass: SatellitePass) {
        _selectedSatellitePass.value = satellitePass
    }

}