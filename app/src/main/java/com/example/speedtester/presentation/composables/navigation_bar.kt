package com.example.speedtester.presentation.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.speedtester.core.util.MyAppDestination

// This is the composable responsible of showing the bottom navigation bar
@Composable
fun NavBar(
    selectedItem: MyAppDestination,
    items: List<MyAppDestination>,
    onClick: (MyAppDestination) -> Unit,
    modifier: Modifier
) {
    NavigationBar(modifier = modifier) {
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.toString()) },
                label = { Text(item.route) },
                selected = selectedItem == item,
                onClick = { onClick(item) },
                alwaysShowLabel = false,
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

