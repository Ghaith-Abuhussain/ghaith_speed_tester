package com.example.speedtester.presentation.screens.speed_test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speedtester.domain.repository.SpeedTestRepository
import com.example.speedtester.ui.theme.FileDownloadUploadNumber
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class SpeedTestViewModel(private val repository: SpeedTestRepository) : ViewModel() {
    // To know if the download speed calculations are finished
    private val _downloadSpeedProcedure = MutableLiveData(true)
    val downloadSpeedProcedure: LiveData<Boolean> get() = _downloadSpeedProcedure

    // To know if the upload speed calculations are finished
    private val _uploadSpeedProcedure = MutableLiveData(true)
    val uploadSpeedProcedure: LiveData<Boolean> get() = _uploadSpeedProcedure

    // the calculated download speed
    private val _downloadSpeed = MutableLiveData<Float>(0f)
    val downloadSpeed: LiveData<Float> get() = _downloadSpeed

    // the calculated upload speed
    private val _uploadSpeed = MutableLiveData<Float>(0f)
    val uploadSpeed: LiveData<Float> get() = _uploadSpeed

    fun testDownloadSpeed() {
        _downloadSpeed.value = 0f
        _downloadSpeedProcedure.value = false
        var sumOfDownloadSpeeds: Long = 0
        viewModelScope.launch {
            try {
                for(i in 1..FileDownloadUploadNumber) {
                    sumOfDownloadSpeeds += repository.downloadSpeedTest()

                    _downloadSpeed.value = ((sumOfDownloadSpeeds / i) * 8) / 1000.0f

                    println("Download Speed: " + _downloadSpeed.value)
                }
                _downloadSpeedProcedure.value = true
            } catch (e: Exception) {
                _downloadSpeedProcedure.value = true
            }

        }
    }

    fun testUploadSpeed(file: RequestBody) {
        _uploadSpeed.value = 0.0f
        _uploadSpeedProcedure.value = false
        var sumOfUploadSpeeds: Long = 0
        viewModelScope.launch {
            try {
                for(i in 1..FileDownloadUploadNumber) {
                    sumOfUploadSpeeds += repository.uploadSpeedTest(file)

                    _uploadSpeed.value = ((sumOfUploadSpeeds / i) * 8) / 1000.0f
                    println("Upload Speed: " + _uploadSpeed.value)
                }
                _uploadSpeedProcedure.value = true
            } catch (e: Exception) {
                _uploadSpeedProcedure.value = true
            }

        }
    }
}