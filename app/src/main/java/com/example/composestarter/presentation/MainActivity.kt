package com.example.composestarter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.composestarter.bottomBar.AppNavigationBar
import com.example.composestarter.graphs.agentsNavGraph
import com.example.composestarter.graphs.favoritesNavGraph
import com.example.composestarter.graphs.mapsNavGraph
import com.example.composestarter.graphs.moreNavGraph
import com.example.composestarter.graphs.weaponsNavGraph
import com.example.composestarter.utils.ScreenRoutes
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
                val lazyListState = rememberLazyListState()
                val scrollingUp = lazyListState.isScrollingUp()
                val lazyGridState = rememberLazyGridState()
                val scrollState = rememberScrollState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            AppNavigationBar(
                                navController = navController,
                                scrollState, lazyGridState, lazyListState
                            )

                        }
                    ) { paddingValues ->

                        NavHost(
                            navController = navController,
                            startDestination = ScreenRoutes.AgentsRouteNavHost,
                            modifier = Modifier.padding(paddingValues),
                            enterTransition = {
                                slideIntoContainer(
                                    animationSpec = tween(500, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    animationSpec = tween(500, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }

                        ) {
                            agentsNavGraph(
                                navController = navController,
                                scrollState = scrollState
                            )
                            mapsNavGraph(
                                navController = navController,
                                lazyScrollState = lazyListState,
                                scrollState = scrollState
                            )
                            weaponsNavGraph(
                                navController = navController,
                                scrollState = scrollState,
                                lazyListState = lazyListState
                            )
                            favoritesNavGraph(
                                lazyListState = lazyListState,
                                scrollState = scrollState
                            )
                            moreNavGraph(
                                navController = navController,
                                lazyListState = lazyListState,
                                lazyGridState = lazyGridState,
                                scrollState = scrollState
                            )
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}


@Composable
fun LazyGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}