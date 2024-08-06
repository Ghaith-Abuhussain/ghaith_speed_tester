package com.example.speedtester.data.repository

import com.example.speedtester.data.data_source.api.SpeedTestService
import com.example.speedtester.domain.repository.SpeedTestRepository
import okhttp3.RequestBody

// this is the implementation of the SpeedTestRepository to download and upload the files from/to the server
class SpeedTestRepositoryImpl(private val service: SpeedTestService) : SpeedTestRepository{
    override suspend fun downloadSpeedTest(): Long {
        val startTime = System.currentTimeMillis()
        val response  = service.downloadTestFile()
        val endTime   = System.currentTimeMillis()

        val fileSize = response.body()?.contentLength() ?: 0

        return fileSize / (endTime - startTime)
    }

    override suspend fun uploadSpeedTest(file: RequestBody): Long {
        val startTime = System.currentTimeMillis()
        val response = service.uploadTestFile(file)
        val endTime = System.currentTimeMillis()
        val fileSize = file.contentLength() * 8
        return fileSize / (endTime - startTime)
    }
}
