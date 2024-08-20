package com.example.speedtester.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.speedtester.core.util.SettingsPage
import com.example.speedtester.core.util.SpeedTestPage
import com.example.speedtester.presentation.screens.settings.SettingsScreen
import com.example.speedtester.presentation.screens.settings.viewmodel.SettingsViewModel
import com.example.speedtester.presentation.screens.speed_test.SpeedTestScreen
import com.example.speedtester.presentation.screens.speed_test.viewmodel.SpeedTestViewModel

// This is the composable that is responsible of moving between the pages of the navigation bar
@Composable
fun BottomNavHost(
    speedType: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    speedTestViewModel: SpeedTestViewModel,
    settingsViewModel: SettingsViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = SpeedTestPage.route,
        modifier = modifier
    ) {
        composable(route = SpeedTestPage.route) {
            SpeedTestScreen(speedType, speedTestViewModel, modifier = Modifier.fillMaxSize())
        }
        composable(route = SettingsPage.route) {
            SettingsScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = settingsViewModel,
            )
        }
    }
}