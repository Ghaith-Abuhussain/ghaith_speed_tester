package com.example.speedtester.domain.repository

import okhttp3.RequestBody

interface SpeedTestRepository {

    suspend fun downloadSpeedTest(): Long

    suspend fun uploadSpeedTest(file: RequestBody): Long

}