package com.example.speedtester.core.util

// This function is used to generate a text file with specified size
fun generateText(sizeInMB: Int): String {
    val sizeInBytes = sizeInMB * 1024 * 1024
    val builder = StringBuilder(sizeInBytes)
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = java.util.Random()

    for (i in 0 until sizeInBytes/8) {
        builder.append(chars[random.nextInt(chars.length)])
    }

    return builder.toString()
}