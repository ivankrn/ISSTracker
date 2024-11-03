package ru.ivankrn.isstracker.ui.screens

import java.time.format.DateTimeFormatter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import ru.ivankrn.isstracker.R
import ru.ivankrn.isstracker.domain.model.SatellitePassWithEvents

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SatellitePassCard(
    dateTimeFormatter: DateTimeFormatter,
    satellitePassWithEvents: SatellitePassWithEvents,
    onCardClick: () -> Unit = {},
    onCardLongClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .combinedClickable(
                onClick = { onCardClick() },
                onLongClick = { onCardLongClick() }
            )
    ) {
        Column {
            Text(
                text = stringResource(
                    R.string.satellite_pass_when,
                    satellitePassWithEvents.riseEvent.utcDatetime.format(dateTimeFormatter)
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = stringResource(
                    R.string.satellite_pass_is_visible,
                    if (satellitePassWithEvents.satellitePass.isVisible) "да" else "нет"
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}