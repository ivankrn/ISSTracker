package ru.ivankrn.isstracker.viewModel.state

import androidx.compose.runtime.Stable

import ru.ivankrn.isstracker.domain.model.SatellitePass

@Stable
interface ListState {
    val items: List<SatellitePass>
    val error: String?
    var loading: Boolean
}