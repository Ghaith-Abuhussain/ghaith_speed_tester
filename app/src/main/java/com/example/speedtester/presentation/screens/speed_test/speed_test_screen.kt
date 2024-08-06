package com.example.speedtester.presentation.screens.speed_test

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedtester.core.util.generateText
import com.example.speedtester.ui.theme.Green200
import com.example.speedtester.ui.theme.Green500
import com.example.speedtester.ui.theme.GreenGradient
import com.example.speedtester.ui.theme.LightColor
import com.example.speedtester.ui.theme.Yellow200
import com.example.speedtester.ui.theme.Yellow500
import com.example.speedtester.ui.theme.YellowGradient
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import kotlin.math.floor

// This function is used to animate the the movements of the speed arch in the circular indicator
suspend fun startAnimation(animationValue: Float, animation: Animatable<Float, AnimationVector1D>) {
    animation.animateTo(animationValue)
}

// This function is used to get the Speed state form the Animatable object
fun Animatable<Float, AnimationVector1D>.toSpeedState(speed: Float, progress: Boolean) = SpeedState(
    arcValue = if (speed > 1) value / 100f else value / 1000f,
    speed = if (speed > 1) "%.1f".format(value) else "%.1f".format(value * 1000f),
    scale = if (speed > 1) "mbps" else "kpbs",
    inProgress = progress
)

// This is the speed screen composable that shows the header, indicators and start button
@Composable
fun SpeedTestScreen(speedType: Int, viewModel: SpeedTestViewModel, modifier: Modifier) {
    // This is used in the lunch effect to start the animation every time the download and upload speed changes
    val coroutineScope = rememberCoroutineScope()

    // The animatable download/upload speed values
    val animationDownload = remember { Animatable(0f) }
    val animationUpload = remember { Animatable(0f) }

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

    // This Lunch effect is used to animate the movements of the archs every time the download and upload speed changes
    LaunchedEffect(downloadSpeed, uploadSpeed) {
        coroutineScope.launch {
            startAnimation(downloadSpeed!!, animationDownload)
        }
        coroutineScope.launch {
            startAnimation(uploadSpeed!!, animationUpload)
        }
    }

    SpeedTestScreen(
        animationDownload.toSpeedState(downloadSpeed!!, downloadSpeedProcedure!!),
        animationUpload.toSpeedState(
            uploadSpeed!!,
            uploadSpeedProcedure!!
        ),
        speedType,
        inProgressUpload = inProgressUpload.value,
        inProgressDownload = inProgressDownload.value,
    ) {
        if(speedType == 1) {
            viewModel.testDownloadSpeed()
        } else if(speedType == 2) {
            val testText = generateText(1)
            val file = RequestBody.create(MediaType.parse("text/plain"), testText)
            viewModel.testUploadSpeed(file)
        } else {
            viewModel.testDownloadSpeed()
            val testText = generateText(1)
            val file = RequestBody.create(MediaType.parse("text/plain"), testText)
            viewModel.testUploadSpeed(file)
        }

    }
}

@Composable
private fun SpeedTestScreen(
    stateDownload: SpeedState,
    stateUpload: SpeedState,
    sppedTestType: Int,
    inProgressUpload: Boolean,
    inProgressDownload: Boolean,
    onClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                StartButton(stateDownload.inProgress && stateUpload.inProgress, onClick)
            }
        },
    ) {paddingValues ->
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
        ) {
            item {
                Header()
                if(sppedTestType == 1)
                    SpeedIndicator(
                        "DOWNLOAD",
                        stateDownload.scale,
                        state = stateDownload,
                        inProgress = inProgressDownload,
                        onClick = onClick
                    )
                else if(sppedTestType == 2)
                    SpeedIndicator("UPLOAD", stateUpload.scale, state = stateUpload, inProgress = inProgressUpload, onClick = onClick)
                else {
                    SpeedIndicator(
                        "DOWNLOAD",
                        stateDownload.scale,
                        state = stateDownload,
                        inProgress = inProgressDownload,
                        onClick = onClick
                    )
                    SpeedIndicator("UPLOAD", stateUpload.scale, state = stateUpload, inProgress = inProgressUpload, onClick = onClick)
                }

            }
        }
    }

}

@Composable
fun Header() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "SPEEDTEST",
            modifier = Modifier.padding(top = 52.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = "speedtest.tele2.net",
            modifier = Modifier.padding(bottom = 20.dp),
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
        )
    }

}

@Composable
fun SpeedIndicator(speedType: String, speedScale: String, state: SpeedState, inProgress: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .aspectRatio(1.0f)
    ) {
        CircularSpeedIndicator(state.arcValue, 240f, inProgress)
        SpeedValue(speedType, speedScale, state.speed)
    }
}

@Composable
fun SpeedValue(speedType: String, speedScale: String, value: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(speedType, style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary))
        Text(
            text = value,
            fontSize = 45.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(speedScale, style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary))
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

@Composable
fun CircularSpeedIndicator(value: Float, angle: Float, inProgress: Boolean) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(80.dp)
    ) {
        drawLines(value, angle)
        drawArcs(value, angle, inProgress)
    }
}

fun DrawScope.drawArcs(progress: Float, maxValue: Float, inProgress: Boolean) {
    val startAngle = 270 - maxValue / 2
    val sweepAngle = maxValue * progress

    val topLeft = Offset(50f, 50f)
    val size = Size(size.width - 100f, size.height - 100f)

    fun drawBlur() {
        for (i in 0..20) {
            drawArc(
                color = if(inProgress) Yellow200.copy(alpha = i / 900f) else Green200.copy(alpha = i / 900f),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = 80f + (20 - i) * 20, cap = StrokeCap.Round)
            )
        }
    }

    fun drawStroke() {
        drawArc(
            color = if(inProgress) Yellow500 else Green500,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = 86f, cap = StrokeCap.Round)
        )
    }

    fun drawGradient() {
        drawArc(
            brush = if(inProgress) YellowGradient else GreenGradient,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = 80f, cap = StrokeCap.Round)
        )
    }

    drawBlur()
    drawStroke()
    drawGradient()
}

fun DrawScope.drawLines(progress: Float, maxValue: Float, numberOfLines: Int = 40) {
    val oneRotation = maxValue / numberOfLines
    val startValue = if (progress == 0f) 0 else floor(progress * numberOfLines).toInt() + 1

    for (i in startValue..numberOfLines) {
        rotate(i * oneRotation + (180 - maxValue) / 2) {
            drawLine(
                LightColor,
                Offset(if (i % 5 == 0) 80f else 30f, size.height / 2),
                Offset(0f, size.height / 2),
                8f,
                StrokeCap.Round
            )
        }
    }
}