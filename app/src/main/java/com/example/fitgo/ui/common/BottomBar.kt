package com.example.apolloproject.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fitgo.ui.navigation.Screens

@Composable
fun BottomBar(
    navController : NavController,
    screens : List<Screens>,



    ) {
    NavigationBar(){
        val state = navController.currentBackStackEntryAsState()
        val currentDestination = state.value?.destination
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                label = { Text(screen.route) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

        }
    }
}