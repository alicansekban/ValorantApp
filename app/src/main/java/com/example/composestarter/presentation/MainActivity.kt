package com.example.composestarter.presentation

import WeaponsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.caseapp.R
import com.example.composestarter.presentation.agents.AgentsScreen
import com.example.composestarter.presentation.agents.detail.AgentDetailScreen
import com.example.composestarter.presentation.bundles.BundlesScreen
import com.example.composestarter.presentation.favorites.FavoritesScreen
import com.example.composestarter.presentation.favorites.agents.FavoriteAgentsScreen
import com.example.composestarter.presentation.favorites.skins.FavoriteSkinsScreen
import com.example.composestarter.presentation.maps.MapsScreen
import com.example.composestarter.presentation.maps.detail.MapsDetailScreen
import com.example.composestarter.presentation.more.MoreScreen
import com.example.composestarter.presentation.ranks.RanksScreen
import com.example.composestarter.presentation.seasons.SeasonsScreen
import com.example.composestarter.presentation.weapons.detail.WeaponDetailScreen
import com.example.composestarter.utils.BottomNavigationItem
import com.example.composestarter.utils.ScreenRoutes
import com.example.composestarter.utils.heightPercent
import com.example.composestarter.utils.theme.ComposeStarterTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
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
                                inclusive = true
                            }
                        }
                    }
                }

                val items = listOf(
                    BottomNavigationItem(
                        title = "Agents",
                        selectedIcon = Icons.Filled.Home,
                        unSelectedIcon = Icons.Outlined.Home,
                        route = ScreenRoutes.AgentsRouteNavHost

                    ),
                    BottomNavigationItem(
                        title = "Maps",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_maps_selected),
                        unSelectedIcon = ImageVector.vectorResource(id = R.drawable.ic_maps_unselected),
                        route = ScreenRoutes.MapsRouteNavHost
                    ),
                    BottomNavigationItem(
                        title = "Weapons",
                        selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_weapons_selected),
                        unSelectedIcon = ImageVector.vectorResource(id = R.drawable.ic_weapons_unselected),
                        route = ScreenRoutes.WeaponsRouteNavHost
                    ),
                    BottomNavigationItem(
                        title = "Favorites",
                        selectedIcon = Icons.Filled.Favorite,
                        unSelectedIcon = Icons.Outlined.FavoriteBorder,
                        route = ScreenRoutes.FavoritesRouteNavHost
                    ),
                    BottomNavigationItem(
                        title = "More",
                        selectedIcon = Icons.Filled.More,
                        unSelectedIcon = Icons.Outlined.More,
                        route = ScreenRoutes.MoreRouteNavHost
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
                            NavigationBar(
                                modifier = Modifier.heightPercent(
                                    0.110f,
                                    LocalConfiguration.current
                                )
                            ) {
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
                                                modifier = Modifier.size(24.dp),
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
                            startDestination = ScreenRoutes.AgentsRouteNavHost,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            navigation(
                                startDestination = ScreenRoutes.AgentsRoute,
                                route = ScreenRoutes.AgentsRouteNavHost
                            ) {
                                composable(
                                    route = ScreenRoutes.AgentsRoute
                                ) { entry ->

                                    AgentsScreen(openDetail = {
                                        navigation(it)
                                    })
                                }
                                composable(ScreenRoutes.AgentsDetailRoute, arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                    }
                                )) { entry ->
                                    val id = entry.arguments?.getString("id")
                                    if (id?.isNotEmpty() == true) {
                                        AgentDetailScreen(
                                            onBackPressed = {
                                                navigation(it)
                                            }
                                        )
                                    }
                                }
                            }
                            navigation(
                                startDestination = ScreenRoutes.MapsRoute,
                                route = ScreenRoutes.MapsRouteNavHost
                            ) {
                                composable(ScreenRoutes.MapsRoute) {
                                    MapsScreen(
                                        openDetail = {
                                            navigation(it)
                                        }
                                    )
                                }
                                composable(ScreenRoutes.MapsDetailRoute, arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                    }
                                )) { entry ->
                                    val id = entry.arguments?.getString("id")
                                    if (id?.isNotEmpty() == true) {
                                        MapsDetailScreen(
                                            onBackPressed = {
                                                navigation(it)
                                            }
                                        )
                                    }
                                }
                            }
                            navigation(
                                startDestination = ScreenRoutes.WeaponsRoute,
                                route = ScreenRoutes.WeaponsRouteNavHost
                            ) {
                                composable(ScreenRoutes.WeaponsRoute) {
                                    WeaponsScreen(
                                        openDetail = {
                                            navigation(it)
                                        }
                                    )
                                }
                                composable(ScreenRoutes.WeaponsDetailRoute, arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.StringType
                                    }
                                )) { entry ->
                                    val id = entry.arguments?.getString("id")
                                    if (id?.isNotEmpty() == true) {
                                        WeaponDetailScreen(
                                            onBackPressed = {
                                                navigation(it)
                                            }
                                        )
                                    }
                                }
                            }
                            navigation(
                                startDestination = ScreenRoutes.FavoritesRoute,
                                route = ScreenRoutes.FavoritesRouteNavHost
                            ) {
                                composable(
                                    route = ScreenRoutes.FavoritesRoute
                                ) { entry ->
                                    FavoritesScreen(
                                        onItemClicked = {
                                            navigation(it)
                                        }
                                    )
                                }
                                composable(
                                    route = ScreenRoutes.FavoriteSkinsRoute
                                ) { entry ->
                                    FavoriteSkinsScreen(
                                        onBackClicked = {
                                            navigation(it)
                                        }
                                    )
                                }
                                composable(
                                    route = ScreenRoutes.FavoriteAgentsRoute
                                ) { entry ->
                                    FavoriteAgentsScreen(
                                        onBackClicked = {
                                            navigation(it)
                                        }
                                    )
                                }
                            }

                            navigation(
                                startDestination = ScreenRoutes.MoreRoute,
                                route = ScreenRoutes.MoreRouteNavHost
                            ) {
                                composable(
                                    route = ScreenRoutes.MoreRoute
                                ) { entry ->
                                    MoreScreen(
                                        onItemClicked = {
                                            navigation(it)
                                        }
                                    )
                                }
                                composable(
                                    route = ScreenRoutes.BundleRoute
                                ) { entry ->
                                    BundlesScreen(
                                        onBackClicked = {
                                            navigation(it)
                                        }
                                    )

                                }
                                composable(
                                    route = ScreenRoutes.RanksRoute
                                ) { entry ->
                                    RanksScreen(
                                        onBackClicked = {
                                            navigation(it)
                                        }
                                    )
                                }

                                composable(
                                    route = ScreenRoutes.SeasonsRoute
                                ) { entry ->
                                    SeasonsScreen(
                                        onBackClicked = {
                                            navigation(it)
                                        }
                                    )
                                }
                            }


                        }
                    }

                }
            }
        }
    }
}