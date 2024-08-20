package com.example.speedtester.presentation.screens.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.speedtester.data.data_source.db.settings.Settings
import com.example.speedtester.domain.repository.SettingsDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsDatabaseRepository: SettingsDatabaseRepository) :
    ViewModel() {

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

    fun updateMode(newMode: Boolean) {
        viewModelScope.launch {
            settingsDatabaseRepository.updateMode(1, newMode)
        }

    }

    fun updateSpeedType(newSpeedType: Int) {
        viewModelScope.launch {
            settingsDatabaseRepository.updateSpeedType(1, newSpeedType)
        }
    }
}