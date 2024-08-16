package com.example.speedtester.core.calculations

import kotlin.math.cos
import kotlin.math.sin

data class Point(val x: Double, val y: Double)

fun calculatePointOnCircle(
    radius: Double,
    centerX: Double,
    centerY: Double,
    angle: Double,
): Point {
    val angleInRadians = Math.toRadians(angle)
    val x = centerX + radius * cos(angleInRadians)
    val y = centerY + radius * sin(angleInRadians)
    return Point(x, y)
}
