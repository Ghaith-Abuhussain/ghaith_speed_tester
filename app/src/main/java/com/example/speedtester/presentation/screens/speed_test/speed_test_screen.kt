package com.example.speedtester.presentation.screens.speed_test

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.speedtester.core.util.generateText
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

// This function is used to animate the the movements of the speed arch in the circular indicator
suspend fun startAnimation(animationValue: Float, animation: Animatable<Float, AnimationVector1D>) {
    animation.animateTo(animationValue, animationSpec = tween(durationMillis = 500))
}

// This is the speed screen composable that shows the header, indicators and start button
@Composable
fun SpeedTestScreen(speedType: Int, viewModel: SpeedTestViewModel, modifier: Modifier, onChangeMode: (newMode: Boolean, newSpeedType: Int) -> Unit) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // This is used in the lunch effect to start the animation every time the download and upload speed changes
    val coroutineScope = rememberCoroutineScope()

    // The Download/Upload speeds calculated by the viewmodel
    val downloadSpeed by viewModel.downloadSpeed.observeAsState()
    val uploadSpeed by viewModel.uploadSpeed.observeAsState()

    // These variable used to know if the calculating is finished or not
    val uploadSpeedProcedure by viewModel.uploadSpeedProcedure.observeAsState()
    val downloadSpeedProcedure by viewModel.downloadSpeedProcedure.observeAsState()

    // These variables are used to change the color of the arch after the calculation is finished
    val inProgressDownload = remember { mutableStateOf(false) }
    val inProgressUpload = remember { mutableStateOf(false) }
    inProgressDownload.value = !downloadSpeedProcedure!!
    inProgressUpload.value = !uploadSpeedProcedure!!

    val animationSpeedDownload = remember { Animatable(0f) }
    val animationSpeedUpload = remember { Animatable(0f) }

    val settings by viewModel.settings.observeAsState()

    LaunchedEffect(settings ) {
        onChangeMode(settings?.mode ?: false, settings?.speedType ?: 1)
    }
    // This Lunch effect is used to animate the movements of the archs every time the download and upload speed changes
    LaunchedEffect(downloadSpeed, uploadSpeed) {
        coroutineScope.launch {
            startAnimation(downloadSpeed!!, animationSpeedDownload)
        }
        coroutineScope.launch {
            startAnimation(uploadSpeed!!, animationSpeedUpload)
        }
    }

    Scaffold(
        bottomBar = {
            if(!isLandscape) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    StartButton(!inProgressDownload.value && !inProgressUpload.value, onClick = {
                        if (speedType == 1) {
                            viewModel.testDownloadSpeed()
                        } else if (speedType == 2) {
                            val testText = generateText(1)
                            val file = RequestBody.create(MediaType.parse("text/plain"), testText)
                            viewModel.testUploadSpeed(file)
                        } else {
                            viewModel.testDownloadSpeed()
                            val testText = generateText(1)
                            val file = RequestBody.create(MediaType.parse("text/plain"), testText)
                            viewModel.testUploadSpeed(file)
                        }
                    })
                }
            }
        },
    ) { paddingValues ->
        if(!isLandscape) {
            PortraitLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues),
                speedType = speedType,
                animationSpeedDownload = animationSpeedDownload,
                animationSpeedUpload = animationSpeedUpload,
                inProgressDownload = inProgressDownload.value,
                inProgressUpload = inProgressUpload.value
            )
        } else {
            LandScapeLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues),
                speedType = speedType,
                animationSpeedDownload = animationSpeedDownload,
                animationSpeedUpload = animationSpeedUpload,
                inProgressDownload = inProgressDownload.value,
                inProgressUpload = inProgressUpload.value,
                content = {
                    StartButton(!inProgressDownload.value && !inProgressUpload.value, onClick = {
                        if (speedType == 1) {
                            viewModel.testDownloadSpeed()
                        } else if (speedType == 2) {
                            val testText = generateText(1)
                            val file = RequestBody.create(MediaType.parse("text/plain"), testText)
                            viewModel.testUploadSpeed(file)
                        } else {
                            viewModel.testDownloadSpeed()
                            val testText = generateText(1)
                            val file = RequestBody.create(MediaType.parse("text/plain"), testText)
                            viewModel.testUploadSpeed(file)
                        }
                    })
                }
            )
        }

    }
}

@Composable
fun Header() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Text(
            text = "SPEEDTEST",
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = "https://file.io",
            modifier = Modifier.padding(bottom = 20.dp),
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
        )
    }

}

@Composable
fun StartButton(isEnabled: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.padding(bottom = 24.dp, top = 10.dp),
        enabled = isEnabled,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface),

        ) {
        Text(
            text = "START",
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )
    }
}
