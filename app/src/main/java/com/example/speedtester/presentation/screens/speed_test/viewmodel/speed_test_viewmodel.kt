package com.example.speedtester.presentation.screens.speed_test.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.speedtester.data.data_source.db.settings.Settings
import com.example.speedtester.domain.repository.SettingsDatabaseRepository
import com.example.speedtester.domain.repository.SpeedTestRepository
import com.example.speedtester.ui.theme.FileDownloadUploadNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SpeedTestViewModel @Inject constructor(private val repository: SpeedTestRepository, private val settingsDatabaseRepository: SettingsDatabaseRepository, @ApplicationContext private val context: Context) : ViewModel() {
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

    private val _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings> get() = _settings
    init {
        // Here we collect the last settings stored in the database and update the _settings mutable live data values to use it in the settings screen
        viewModelScope.launch {
            settingsDatabaseRepository.getSettingsById(1).asFlow().collect {
                _settings.value = it
            }
        }
    }

    fun testDownloadSpeed() {
        _downloadSpeed.value = 0f
        _downloadSpeedProcedure.value = false
        var sumOfDownloadSpeeds: Long = 0
        viewModelScope.launch {
            try {
                for(i in 1..FileDownloadUploadNumber) {
                    sumOfDownloadSpeeds += repository.downloadSpeedTest(context)

                    _downloadSpeed.value = ((sumOfDownloadSpeeds / i) * 8) / 1000.0f

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
                    sumOfUploadSpeeds += repository.uploadSpeedTest(context)

                    _uploadSpeed.value = ((sumOfUploadSpeeds / i) * 8) / 1000.0f
                }
                _uploadSpeedProcedure.value = true
            } catch (e: Exception) {
                _uploadSpeedProcedure.value = true
            }

        }
    }
}