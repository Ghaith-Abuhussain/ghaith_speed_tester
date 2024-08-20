package com.example.speedtester.data.repository

import android.content.Context
import com.example.speedtester.data.data_source.api.SpeedTestService
import com.example.speedtester.domain.repository.SpeedTestRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

// this is the implementation of the SpeedTestRepository to download and upload the files from/to the server

class SpeedTestRepositoryImpl @Inject constructor(private val service: SpeedTestService) : SpeedTestRepository{
    override suspend fun downloadSpeedTest(context: Context): Long {
        val assetManager = context.assets

        val inputStreamEstimation = assetManager.open("0B.zip")

        // Upload a file to download it later because the API allows us to download 1 time without pricing.
        val inputStream = assetManager.open("1MB.zip")
        val file = File(context.cacheDir, "1MB.zip")
        file.outputStream().use { inputStream.copyTo(it) }
        val requestFile = RequestBody.create(MediaType.parse("application/zip"), file)
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val uploadResponse = service.uploadTestFile(filePart)

        if(uploadResponse.code() == 200) {
            // Estimate the server response time by uploading a zero bytes file
            val fileZeroSize = File(context.cacheDir, "0B.zip")
            fileZeroSize.outputStream().use { inputStreamEstimation.copyTo(it) }
            val startTimeEstimation = System.currentTimeMillis()
            val requestFileEstimate = RequestBody.create(MediaType.parse("application/zip"), fileZeroSize)
            val filePartEstimate = MultipartBody.Part.createFormData("file", fileZeroSize.name, requestFileEstimate)
            val uploadResponseEstimation = service.uploadTestFile(filePartEstimate)
            val endTimeEstimation = System.currentTimeMillis()
            val serverResponse = (endTimeEstimation - startTimeEstimation);

            // Calculate the Download time with the server response
            val startTime = System.currentTimeMillis()
            val downloadResponse = service.downloadTestFile(uploadResponse.body()!!.key)
            val endTime = System.currentTimeMillis()

            return if(downloadResponse.code() == 200) {
                 if(((endTime - startTime) - serverResponse) > 0) requestFile.contentLength() / ((endTime - startTime) - serverResponse) else 0
            } else {
                0L
            }
        } else {
            return 0L
        }
    }

    override suspend fun uploadSpeedTest(context: Context): Long {

        try {
            val assetManager = context.assets

            // Estimate the server response time by uploading a zero bytes file
            val inputStreamEstimation = assetManager.open("0B.zip")
            val fileZeroSize = File(context.cacheDir, "0B.zip")
            fileZeroSize.outputStream().use { inputStreamEstimation.copyTo(it) }
            val startTimeEstimation = System.currentTimeMillis()
            val requestFileEstimate = RequestBody.create(MediaType.parse("application/zip"), fileZeroSize)
            val filePartEstimate = MultipartBody.Part.createFormData("file", fileZeroSize.name, requestFileEstimate)
            val uploadResponseEstimation = service.uploadTestFile(filePartEstimate)
            val endTimeEstimation = System.currentTimeMillis()
            val serverResponse = (endTimeEstimation - startTimeEstimation);

            val inputStream = assetManager.open("1MB.zip")
            val file = File(context.cacheDir, "1MB.zip")
            file.outputStream().use { inputStream.copyTo(it) }
            val requestFile = RequestBody.create(MediaType.parse("application/zip"), file)
            val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
            // Upload the 1MB file to the server and calculate the upload speed (with the server response time).
            val startTime = System.currentTimeMillis()
            val response = service.uploadTestFile(filePart)
            val endTime = System.currentTimeMillis()

            return if(response.code() == 200) if(((endTime - startTime) - serverResponse) > 0) requestFile.contentLength() / ((endTime - startTime) - serverResponse) else 0 else 0
        } catch (e: Exception) {
            println("${e.message}")
            return 0
        }

    }
}
