package com.example.composestarter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.caseapp.R
import com.example.composestarter.graphs.agentsNavGraph
import com.example.composestarter.graphs.favoritesNavGraph
import com.example.composestarter.graphs.mapsNavGraph
import com.example.composestarter.graphs.moreNavGraph
import com.example.composestarter.graphs.weaponsNavGraph
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
                        navController.navigate(route)
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
                    mutableIntStateOf(0)
                }
                val lazyListState = rememberLazyListState()
                val lazyGridState = rememberLazyGridState()
                val scrollState = rememberScrollState()
                val isScrolling by remember(lazyListState, lazyGridState, scrollState) {
                    derivedStateOf { lazyListState.isScrollInProgress || lazyGridState.isScrollInProgress || scrollState.isScrollInProgress }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                visible = !isScrolling,
                            ) {
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

                        }
                    ) { paddingValues ->

                        NavHost(
                            navController = navController,
                            startDestination = ScreenRoutes.AgentsRouteNavHost,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            agentsNavGraph(navController, scrollState)
                            mapsNavGraph(navController, lazyListState)
                            weaponsNavGraph(navController, scrollState, lazyListState)
                            favoritesNavGraph(lazyListState, scrollState)
                            moreNavGraph(navController, lazyListState, lazyGridState, scrollState)
                        }
                    }

                }
            }
        }
    }
}