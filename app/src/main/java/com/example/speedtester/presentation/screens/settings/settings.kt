package com.example.speedtester.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedtester.presentation.composables.DarkModeSwitch
import com.example.speedtester.presentation.composables.SpeedTestChoosingRadioButton

// This is the settings screen composable
@Composable
fun SettingsScreen(viewModel: SettingsViewModel, modifier: Modifier = Modifier, onChangeMode: (newMode: Boolean, newSpeedType: Int) -> Unit) {
    // The current state of the settings in the database driven fom the viewmodel
    val settings by viewModel.settings.observeAsState()

    // The Current value of the theme (Dark/Light)
    var value by remember { mutableStateOf(settings?.mode ?: false) }
    // The Current type of the spped test (download, upload, both)
    var speedTestType by remember { mutableStateOf(settings?.speedType ?: 1) }

    // The options of the radio buttons
    val options = listOf("Download", "Upload", "Both")
    // The current selected option of the radio button
    var selectedOption by remember { mutableStateOf(options[speedTestType - 1]) }

    LaunchedEffect(settings) {
        onChangeMode(settings?.mode ?: false, settings?.speedType ?: 1)
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            Text(
                text = "SETTINGS",
                modifier = Modifier.padding(top = 52.dp, bottom = 20.dp),
                style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(300.dp)) {
                Text(text = "MODE", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary, fontSize = 20.sp))
                DarkModeSwitch(!(settings?.mode ?: false),
                    Modifier
                        .padding(24.dp)
                        .width(160.dp)
                        .height(60.dp)) {
                    value = !it
                    viewModel.updateMode(value)
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.width(300.dp)) {
                Text(text = "Choose Speed Type", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary, fontSize = 20.sp))
                SpeedTestChoosingRadioButton(selectedOption, options, onClick = {
                    speedTestType = if(it == options[0]) 1 else if(it == options[1]) 2 else 3
                    selectedOption = options[speedTestType - 1]
                    viewModel.updateSpeedType(speedTestType)
                })
            }

        }
    }
}