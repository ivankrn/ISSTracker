package ru.ivankrn.isstracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ivankrn.isstracker.R
import ru.ivankrn.isstracker.model.AstronomyEvent
import ru.ivankrn.isstracker.model.SatellitePass
import java.time.format.DateTimeFormatter

@Composable
fun SatellitePassDetailsScreen(
    satellitePass: SatellitePass
) {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = stringResource(R.string.satellite_pass_details_info_header),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        SatellitePassPhaseCard(
            phase = stringResource(R.string.satellite_pass_rise_phase),
            phaseIconId = R.drawable.arrow_up,
            data = satellitePass.riseEvent,
            dateTimeFormatter = dateTimeFormatter
        )
        Spacer(modifier = Modifier.height(16.dp))
        SatellitePassPhaseCard(
            phase = stringResource(R.string.satellite_pass_culmination_phase),
            phaseIconId = R.drawable.circle,
            data = satellitePass.culminationEvent,
            dateTimeFormatter = dateTimeFormatter
        )
        Spacer(modifier = Modifier.height(16.dp))
        SatellitePassPhaseCard(
            phase = stringResource(R.string.satellite_pass_set_phase),
            phaseIconId = R.drawable.arrow_down,
            data = satellitePass.setEvent,
            dateTimeFormatter = dateTimeFormatter
        )
    }
}

@Composable
fun SatellitePassPhaseCard(
    phase: String,
    phaseIconId: Int,
    data: AstronomyEvent,
    dateTimeFormatter: DateTimeFormatter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = phaseIconId),
                contentDescription = "$phase icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = phase, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.satellite_pass_altitude, data.altitude),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.satellite_pass_azimuth, data.az),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.satellite_pass_azimuth_octant, data.azOctant),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.satellite_pass_datetime, dateTimeFormatter.format(data.utcDatetime)),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(
                        R.string.satellite_pass_is_visible,
                        if (data.isVisible) "да" else "нет"
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(
                        R.string.satellite_pass_is_sunlit,
                        if (data.isSunlit) "да" else "нет"
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}