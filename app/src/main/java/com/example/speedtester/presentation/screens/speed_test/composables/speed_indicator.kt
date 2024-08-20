package com.example.speedtester.presentation.screens.speed_test.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp
import com.example.speedtester.core.calculations.calculatePointOnCircle
import com.example.speedtester.ui.theme.Green500
import com.example.speedtester.ui.theme.GreenGradient
import com.example.speedtester.ui.theme.Yellow500
import com.example.speedtester.ui.theme.YellowGradient

@Composable
fun SpeedIndicatorWithTitle(
    modifier: Modifier = Modifier,
    speedValue: Float,
    speedMode: String,
    inProgress: Boolean,
    uploadOrDownload: String,
    speedModeStyle: TextStyle,
    speedValueStyle: TextStyle,
    speedScaleStyle: TextStyle,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        SpeedIndicator(
            modifier = Modifier.fillMaxSize(),
            startAngle = -240,
            endAngle = 60,
            speedMode = speedMode,
            speedValue = if (speedValue >= 1.2) speedValue * 5 / 2 else speedValue * 1000,
            inProgress = inProgress
        )
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(uploadOrDownload, style = speedModeStyle,)
            Text(
                text = "%.1f".format(if (speedValue >= 1.2) speedValue else speedValue * 1000),
                style = speedValueStyle
            )
            Text(speedMode, style = speedScaleStyle)
        }
    }
}

@Composable
fun SpeedIndicator(
    modifier: Modifier = Modifier,
    startAngle: Int,
    endAngle: Int,
    speedMode: String,
    speedValue: Float,
    inProgress: Boolean
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val farRadius = (size.width / 2).toDouble()
        for (angle in startAngle..endAngle step 5) {

            val closeRadius = if ((angle + (-1 * startAngle)) % 25 == 0) {
                (size.width / 2).toDouble() * 80 / 100
            } else {
                (size.width / 2).toDouble() * 90 / 100
            }

            val Point1 = calculatePointOnCircle(
                closeRadius,
                canvasWidth / 2.0,
                canvasWidth / 2.0,
                angle.toDouble(),
            )

            val Point2 = calculatePointOnCircle(
                farRadius,
                canvasWidth / 2.0,
                canvasWidth / 2.0,
                angle.toDouble(),
            )

            val textPoint = calculatePointOnCircle(
                farRadius * 1.2,
                canvasWidth / 2.0,
                canvasWidth / 2.0,
                angle.toDouble(),
            )

            if ((angle - 1 * startAngle) % 25 == 0) {
                val text =
                    if (speedMode.equals("mbps")) "${2 * (angle + (-1 * startAngle)) / 5}" else "${4 * (angle + (-1 * startAngle))}"
                val textWidth = ((canvasWidth * 0.02) * text.length).sp.toPx().toInt()
                val textHeight =
                    canvasWidth.toSp().toString().substringBefore(".sp").toFloat().toInt() / 3
                drawText(
                    textMeasurer.measure(
                        text,
                        style = TextStyle(
                            fontSize = (canvasWidth * 0.02).sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        ),
                        constraints = Constraints(
                            minHeight = textHeight,
                            minWidth = textWidth,
                            maxHeight = textHeight,
                            maxWidth = textWidth
                        )
                    ), topLeft = Offset(
                        x = (textPoint.x - (canvasWidth * 0.02) * text.length).toFloat(),
                        y = (textPoint.y - textHeight / 2).toFloat()
                    )
                )
            }

            drawLine(
                start = Offset(
                    x = Point1.x.toFloat(),
                    y = Point1.y.toFloat()
                ),
                end = Offset(
                    x = Point2.x.toFloat(),
                    y = Point2.y.toFloat()
                ),
                color = Color.Blue
            )
        }

        drawArc(
            color = if (inProgress) Yellow500 else Green500,
            startAngle = (startAngle).toFloat(),
            sweepAngle = if (speedMode.equals("mbps")) speedValue else speedValue / 4,
            useCenter = false,
            topLeft = Offset(
                x = 0f,
                y = 0f
            ),
            size = Size(width = canvasWidth, height = canvasWidth),
            style = Stroke(width = 30f, cap = StrokeCap.Round)
        )

        drawArc(
            brush = if (inProgress) YellowGradient else GreenGradient,
            startAngle = (startAngle).toFloat(),
            sweepAngle = if (speedMode.equals("mbps")) speedValue else speedValue / 4,
            useCenter = false,
            topLeft = Offset(
                x = 0f,
                y = 0f
            ),
            size = Size(width = canvasWidth, height = canvasWidth),
            style = Stroke(width = 30f, cap = StrokeCap.Round)
        )


    }
}
