package ru.ivankrn.isstracker.ui.screens

import android.widget.Toast
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString

import ru.ivankrn.isstracker.R
import ru.ivankrn.isstracker.domain.model.SatellitePassWithEvents
import ru.ivankrn.isstracker.viewModel.SatellitePassViewModel

@Composable
fun SatellitePassesListScreen(
    viewModel: SatellitePassViewModel,
    onNavigateToDetails: (SatellitePassWithEvents) -> Unit
) {
    val state = viewModel.viewState
    val mContext = LocalContext.current
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
                items(state.items) { satellitePassWithEvents ->
                    SatellitePassCard(
                        dateTimeFormatter,
                        satellitePassWithEvents,
                        onCardClick = {
                            viewModel.updateSelectedSatellitePass(satellitePassWithEvents)
                            onNavigateToDetails(satellitePassWithEvents)
                        },
                        onCardLongClick = {
                            if (satellitePassWithEvents.satellitePass.isFavorite) {
                                viewModel.removeFromFavorites(satellitePassWithEvents)
                                Toast.makeText(
                                    mContext,
                                    getString(mContext, R.string.removed_from_favorites),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                viewModel.addToFavorites(satellitePassWithEvents)
                                Toast.makeText(
                                    mContext,
                                    getString(mContext, R.string.added_to_favorites),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }
        }
    }

    if (state.loading) {
        FullScreenProgress()
    }

}