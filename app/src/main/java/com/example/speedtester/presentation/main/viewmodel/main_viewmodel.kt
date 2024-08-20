package com.example.speedtester.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.speedtester.domain.repository.SettingsDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(private val settingsDatabaseRepository: SettingsDatabaseRepository): ViewModel(){
    val settings = settingsDatabaseRepository.getSettingsById(1)
}