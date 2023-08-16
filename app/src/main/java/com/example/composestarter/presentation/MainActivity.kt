package com.example.composestarter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.More
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composestarter.utils.BottomNavigationItem
import com.example.composestarter.utils.theme.ComposeStarterTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStarterTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                val navigation: (String) -> Unit = { route ->
                    if (route == "-1") {
                        navController.popBackStack()
                    } else {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }

                val items = listOf(
                    BottomNavigationItem(
                        title = "Agents",
                        selectedIcon = Icons.Filled.Home,
                        unSelectedIcon = Icons.Outlined.Home,
                        route = "Agents"

                    ),
                    BottomNavigationItem(
                        title = "Maps",
                        selectedIcon = Icons.Filled.Album,
                        unSelectedIcon = Icons.Outlined.Album,
                        route = "Maps"
                    ),
                    BottomNavigationItem(
                        title = "Skins",
                        selectedIcon = Icons.Filled.More,
                        unSelectedIcon = Icons.Outlined.More,
                        route = "Skins"
                    ),
                )
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                           navigation(item.route)


                                        },
                                        label = {
                                            Text(
                                                text = item.title
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unSelectedIcon,
                                                contentDescription = item.title
                                            )
                                        })
                                }
                            }
                        }
                    ) { paddingValues ->

                        NavHost(
                            navController = navController,
                            startDestination = "Agents",
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable("Agents") {
                                AgentsScreen()
                            }
                            composable("Maps") {
                                MapsScreen()
                            }
                            composable("Skins") {
                                SkinsScreen()
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun AgentsScreen() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Agent Screen")
    }
}

@Composable
fun MapsScreen() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Maps Screen")
    }
}

@Composable
fun SkinsScreen() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Skins Screen")
    }
}