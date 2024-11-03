package ru.ivankrn.isstracker.viewModel.state

import androidx.compose.runtime.Stable

import ru.ivankrn.isstracker.domain.model.SatellitePassWithEvents

@Stable
interface ListState {
    val items: List<SatellitePassWithEvents>
    val favoriteItems: List<SatellitePassWithEvents>
    val error: String?
    var loading: Boolean
}