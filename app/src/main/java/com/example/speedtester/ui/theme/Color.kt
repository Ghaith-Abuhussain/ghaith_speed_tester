package com.example.speedtester.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BlueSky= Color(0xFF4478a9)
val NightSky =  Color(0xFF333333)
val BorderColor = Color(0x40000000)

val Indigo = Color(0xFF6200EE)
val DarkIndigo = Color(0xFF3700B3)
val Teal = Color(0xFF03DAC6)
val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFF5F5F5)
val Red = Color(0xFFB00020)
val Black = Color(0xFF000000)
val LightPurple = Color(0xFFBB86FC)
val DarkGray = Color(0xFF121212)
val DarkerGray = Color(0xFF1E1E1E)
val LightRed = Color(0xFFCF6679)
val Orange = Color(0xFFFF9800)

val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Pink = Color(0xFFE2437E)

val Green200 = Color(0xFFAEFF82)
val Green300 = Color(0xFFC9FCAD)
val Green500 = Color(0xFF07A312)

val Yellow200 = Color(0xFFFFF59D)
val Yellow300 = Color(0xFFFFF176)
val Yellow500 = Color(0xFFFFEB3B)


val DarkColor = Color(0xFF101522)
val DarkColor2 = Color(0xFF202532)
val LightColor = Color(0xFF414D66)
val LightColor2 = Color(0xFF626F88)

val GreenGradient = Brush.linearGradient(
    colors = listOf(Green300, Green200),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val YellowGradient = Brush.linearGradient(
    colors = listOf(Yellow300, Yellow200),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val DarkGradient = Brush.verticalGradient(
    colors = listOf(DarkColor2, DarkColor)
)