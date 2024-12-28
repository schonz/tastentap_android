package com.tastentap.tastentap_android_app.ui

import com.tastentap.tastentap_android_app.R
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.composable
import com.tastentap.tastentap_android_app.ui.screens.*


@Composable
fun Scaffold() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationHost(navController, Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val items = listOf(
            NavigationItem.Explore,
            NavigationItem.Inventory,
            NavigationItem.Settings
        )

        val currentDestination = navController.currentDestination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { NavigationIcon(item.icon, item.label) },
                label = { Text(item.label)},
                selected = currentDestination == item.route,
                onClick = {
                    if (currentDestination != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }

    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Explore.route,
        modifier = modifier
    ) {
        composable(NavigationItem.Explore.route) { ExploreScreen() }
        composable(NavigationItem.Inventory.route) { InventoryScreen() }
        composable(NavigationItem.Settings.route) { SettingsScreen() }
    }
}

@Composable
fun NavigationIcon(icon: Any, contentDescription: String?) {
    when (icon) {
        is Int -> Icon(
            painter = painterResource(icon), // Drawable resource
            contentDescription = contentDescription
        )
        is ImageVector -> Icon(
            imageVector = icon, // Vector icon
            contentDescription = contentDescription
        )
        else -> throw IllegalArgumentException("Unsupported icon type")
    }
}

sealed class NavigationItem(val route: String, val icon: Any, val label: String){
    object Explore: NavigationItem(
        "explore",
        R.drawable.explore_24dp,
        "Explore")
    object Inventory: NavigationItem(
        "inventory",
        R.drawable.shelves_24dp,
        "Inventory")
    object Settings: NavigationItem(
        "settings",
        Icons.Filled.Settings,
        "Settings")
}