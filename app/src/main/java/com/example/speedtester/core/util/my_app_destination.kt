package com.example.speedtester.core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.ui.graphics.vector.ImageVector

// This interface is used for the navigation bar. The navigation bar is meant to navigate between two Pages (SpeedTestPage, SettingsPage) by specifying the route
interface MyAppDestination{
    val icon: ImageVector
    val route:String
}

object SpeedTestPage : MyAppDestination {
    override val icon = Icons.Filled.Speed
    override val route = "Speed"
    //override val screen: @Composable () -> Unit = { OverviewScreen() }
}

object SettingsPage : MyAppDestination {
    override val icon = Icons.Filled.Settings
    override val route = "Settings"
    //override val screen: @Composable () -> Unit = { AccountsScreen() }
}

val bottomBarTabScreens = listOf(SpeedTestPage, SettingsPage)