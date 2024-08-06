package com.example.speedtester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.asFlow
import com.example.speedtester.data.data_source.api.SpeedTestService
import com.example.speedtester.data.data_source.db.settings.DatabaseProvider
import com.example.speedtester.data.data_source.db.settings.SettingsDao
import com.example.speedtester.data.repository.SettingsDatabaseRepositoryImpl
import com.example.speedtester.data.repository.SpeedTestRepositoryImpl
import com.example.speedtester.presentation.app.App
import com.example.speedtester.presentation.splash.SplashScreen
import com.example.speedtester.ui.theme.SpeedTesterTheme
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingsDao: SettingsDao = DatabaseProvider.getDatabase(applicationContext).settingsDao()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://speedtest.tele2.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SpeedTestService::class.java)
        val speedTestRepository = SpeedTestRepositoryImpl(service)
        val settingsDatabaseRepository = SettingsDatabaseRepositoryImpl(settingsDao)


        setContent {
            var mode by remember {
                mutableStateOf(false)
            }

            var speedTestType by remember {
                mutableStateOf(1)
            }

            LaunchedEffect(true) {
                settingsDatabaseRepository.getSettingsById(1).asFlow().collectLatest {
                    mode = it!!.mode
                    speedTestType = it!!.speedType
                }
            }
            println(mode)
            SpeedTesterTheme(darkTheme = !mode) {
                // A surface container using the 'background' color from the theme
                var showSplashScreen by rememberSaveable { mutableStateOf(true) }

                if (showSplashScreen) {
                    SplashScreen(modifier = Modifier.fillMaxSize().safeDrawingPadding(), ) {
                        showSplashScreen = false
                    }
                } else {
                    App(
                        speedTestType,
                        speedTestRepository = speedTestRepository,
                        settingsDatabaseRepository = settingsDatabaseRepository
                    )
                }
            }
        }
    }
}
