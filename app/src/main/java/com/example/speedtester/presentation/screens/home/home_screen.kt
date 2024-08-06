package com.example.speedtester.presentation.screens.home

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.speedtester.core.util.SpeedTestPage
import com.example.speedtester.core.util.bottomBarTabScreens
import com.example.speedtester.presentation.composables.BottomNavHost
import com.example.speedtester.presentation.composables.NavBar
import com.example.speedtester.presentation.composables.navigateSingleTopTo
import com.example.speedtester.presentation.screens.settings.SettingsViewModel
import com.example.speedtester.presentation.screens.speed_test.SpeedTestViewModel

// This is the home screen that contains the Bottom navigation host
@Composable
fun HomeScreen(
    speedType: Int,
    modifier: Modifier,
    speedTestViewModel: SpeedTestViewModel,
    settingsViewModel: SettingsViewModel,
) {
    // the controller of the navigation host
    val bottomNavController = rememberNavController()
    val currentBackStack by bottomNavController.currentBackStackEntryAsState()
    // the currentDestination (the page that the navigation bar shows on the screen)
    val currentDestination = currentBackStack?.destination
    // The screen that the navigation host shows
    val currentScreen =
        bottomBarTabScreens.find { it.route == currentDestination?.route } ?: SpeedTestPage

    Scaffold(bottomBar = {
        NavBar(
            items = bottomBarTabScreens,
            onClick = { newScreen -> bottomNavController.navigateSingleTopTo(newScreen.route) },
            selectedItem = currentScreen,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .heightIn(min = 30.dp, max = 80.dp)
        )
    }, modifier = modifier) { paddingValues ->
        BottomNavHost(
            speedType,
            navController = bottomNavController,
            modifier = modifier.padding(paddingValues),
            speedTestViewModel = speedTestViewModel,
            settingsViewModel = settingsViewModel,
        )

    }
}