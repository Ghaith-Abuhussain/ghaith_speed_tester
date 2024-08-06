package com.example.speedtester.presentation.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.speedtester.domain.repository.SettingsDatabaseRepository
import com.example.speedtester.domain.repository.SpeedTestRepository
import com.example.speedtester.presentation.screens.home.HomeScreen
import com.example.speedtester.presentation.screens.settings.SettingsViewModel
import com.example.speedtester.presentation.screens.speed_test.SpeedTestViewModel

// This is the main app repository that takes in its constructor the speedType (the variable that indicates which type of speed estimate we want to show "Upload, Download, Both")
// speedTestRepository (to perform the download/upload functionalities), settingsDatabaseRepository (to perform the database manipulation functionalities)
@Composable
fun App(speedType: Int, speedTestRepository: SpeedTestRepository, settingsDatabaseRepository: SettingsDatabaseRepository) {

    // Here we declare the viewmodels of the SpeedTestScreen and the SettingsScreen
    val speedViewModel = SpeedTestViewModel(speedTestRepository)
    val settingsViewModel = SettingsViewModel(settingsDatabaseRepository)

    Scaffold(
        modifier = Modifier,
    ) { paddingValues ->
        // Here we show the HomeScreen that contains the navigation Bar
        HomeScreen(speedType, Modifier.padding(paddingValues), speedViewModel, settingsViewModel)
    }
}