package ru.ivankrn.isstracker.ui.screens

import java.time.format.DateTimeFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import ru.ivankrn.isstracker.R
import ru.ivankrn.isstracker.domain.model.SatellitePass
import ru.ivankrn.isstracker.viewModel.SatellitePassViewModel

@Composable
fun SatellitePassesListScreen(
    viewModel: SatellitePassViewModel,
    onNavigateToDetails: (SatellitePass) -> Unit
) {
    val state = viewModel.viewState

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = stringResource(R.string.upcoming_iss_passes_header),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)
        ) {
            state.error?.let {
                Text(text = it)
            }

            LazyColumn(
                contentPadding = PaddingValues(4.dp)
            ) {
                items(state.items) { satellitePass ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable {
                                viewModel.updateSelectedSatellitePass(satellitePass)
                                onNavigateToDetails(satellitePass)
                            }
                    ) {
                        Column {
                            Text(
                                text = stringResource(
                                    R.string.satellite_pass_when,
                                    satellitePass.riseEvent.utcDatetime.format(dateTimeFormatter)
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = stringResource(
                                    R.string.satellite_pass_is_visible,
                                    if (satellitePass.isVisible) "да" else "нет"
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }

    if (state.loading) {
        FullScreenProgress()
    }

}