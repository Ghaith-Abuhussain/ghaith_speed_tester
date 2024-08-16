package com.example.speedtester.domain.repository

import android.content.Context

interface SpeedTestRepository {

    suspend fun downloadSpeedTest(context: Context): Long

    suspend fun uploadSpeedTest(context: Context): Long

}