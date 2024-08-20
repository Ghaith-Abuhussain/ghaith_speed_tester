package com.example.speedtester.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.speedtester.presentation.main.viewmodel.MainViewModel
import com.example.speedtester.presentation.screens.home.HomeScreen
import com.example.speedtester.presentation.screens.settings.viewmodel.SettingsViewModel
import com.example.speedtester.presentation.screens.speed_test.viewmodel.SpeedTestViewModel
import com.example.speedtester.presentation.splash.SplashScreen
import com.example.speedtester.ui.theme.SpeedTesterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val speedViewModel: SpeedTestViewModel = hiltViewModel()
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val mainViewModel: MainViewModel = hiltViewModel()
            val settings by mainViewModel.settings.observeAsState()
            SpeedTesterTheme(darkTheme = settings?.mode ?: false) {
                // A surface container using the 'background' color from the theme
                var showSplashScreen by rememberSaveable { mutableStateOf(true) }

                if (showSplashScreen) {
                    SplashScreen(modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(), ) {
                        showSplashScreen = false
                    }
                } else {
                    Scaffold(
                        modifier = Modifier,
                    ) { paddingValues ->
                        // Here we show the HomeScreen that contains the navigation Bar
                        HomeScreen(
                            settings?.speedType ?: 1,
                            Modifier.padding(paddingValues),
                            speedViewModel,
                            settingsViewModel,
                        )
                    }
                }
            }
        }
    }
}
