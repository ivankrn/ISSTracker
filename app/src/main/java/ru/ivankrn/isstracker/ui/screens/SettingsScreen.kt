package ru.ivankrn.isstracker.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString

import ru.ivankrn.isstracker.R
import ru.ivankrn.isstracker.viewModel.SatellitePassViewModel

@Composable
fun SettingsScreen(
    viewModel: SatellitePassViewModel
) {
    var latitude by remember { mutableStateOf(TextFieldValue(viewModel.latitude.value.toString())) }
    var longitude by remember { mutableStateOf(TextFieldValue(viewModel.longitude.value.toString())) }
    var isSettingsSaved by remember { mutableStateOf(false) }

    val mContext = LocalContext.current
    val onlyDigits = Regex("[\\d,]*[.]?[\\d,]*")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.settings_header), style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = latitude,
            onValueChange = {
                if (it.text.matches(onlyDigits)) {
                    latitude = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.latitude_label)) },
            placeholder = { Text(stringResource(R.string.latitude_textfield_placeholder)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = longitude,
            onValueChange = {
                if (it.text.matches(onlyDigits)) {
                    longitude = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.longitude_label)) },
            placeholder = { Text(stringResource(R.string.longitude_textfield_placeholder)) }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (latitude.text.isBlank() || longitude.text.isBlank()) {
                    Toast.makeText(mContext, getString(mContext, R.string.lat_lon_validate_message), Toast.LENGTH_SHORT)
                        .show()
                }
                viewModel.saveLocation(latitude.text.toBigDecimal(), longitude.text.toBigDecimal())
                isSettingsSaved = true
            },
        ) {
            Text(text = stringResource(R.string.save_settings_button))
        }

        if (isSettingsSaved) {
            Text(
                text = stringResource(R.string.settings_have_been_saved),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}