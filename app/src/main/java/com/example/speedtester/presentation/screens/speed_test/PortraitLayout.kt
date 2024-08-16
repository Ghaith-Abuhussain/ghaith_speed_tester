package com.example.speedtester.presentation.screens.speed_test

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PortraitLayout(
    modifier: Modifier = Modifier,
    speedType: Int,
    animationSpeedDownload: Animatable<Float, AnimationVector1D>,
    animationSpeedUpload: Animatable<Float, AnimationVector1D>,
    inProgressDownload: Boolean,
    inProgressUpload: Boolean
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Header()
            when (speedType) {
                1 -> Box(
                    modifier = Modifier
                        .width(350.dp)
                        .height(350.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SpeedIndicatorWithTitle(
                        modifier = Modifier.size(width = 300.dp, height = 300.dp),
                        speedMode = if (animationSpeedDownload.value >= 1.2) "mbps" else "kbps",
                        speedValue = animationSpeedDownload.value,
                        inProgress = inProgressDownload,
                        uploadOrDownload = "DOWNLOAD",
                        speedModeStyle = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 25.sp,
                        ),
                        speedScaleStyle = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        speedValueStyle = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                2 -> Box(
                    modifier = Modifier
                        .width(350.dp)
                        .height(350.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SpeedIndicatorWithTitle(
                        modifier = Modifier.size(width = 300.dp, height = 300.dp),
                        speedMode = if (animationSpeedUpload.value >= 1.2) "mbps" else "kbps",
                        speedValue = animationSpeedUpload.value,
                        inProgress = inProgressUpload,
                        uploadOrDownload = "UPLOAD",
                        speedModeStyle = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 25.sp,
                        ),
                        speedScaleStyle = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        speedValueStyle = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .width(165.dp)
                            .height(165.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SpeedIndicatorWithTitle(
                            modifier = Modifier.fillMaxSize(),
                            speedMode = if (animationSpeedDownload.value >= 1.2) "mbps" else "kbps",
                            speedValue = animationSpeedDownload.value,
                            inProgress = inProgressDownload,
                            uploadOrDownload = "DOWNLOAD",
                            speedModeStyle = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 15.sp
                            ),
                            speedScaleStyle = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            speedValueStyle = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Box(
                        modifier = Modifier
                            .width(165.dp)
                            .height(165.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SpeedIndicatorWithTitle(
                            modifier = Modifier.fillMaxSize(),
                            speedMode = if (animationSpeedUpload.value >= 1.2) "mbps" else "kbps",
                            speedValue = animationSpeedUpload.value,
                            inProgress = inProgressUpload,
                            uploadOrDownload = "UPLOAD",
                            speedModeStyle = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 15.sp
                            ),
                            speedScaleStyle = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            speedValueStyle = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}