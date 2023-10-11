package com.example.composestarter.graphs

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composestarter.presentation.bundles.BundlesScreen
import com.example.composestarter.presentation.more.MoreScreen
import com.example.composestarter.presentation.ranks.RanksScreen
import com.example.composestarter.presentation.seasons.SeasonsScreen
import com.example.composestarter.utils.ScreenRoutes

fun NavGraphBuilder.moreNavGraph(
    navController: NavController,
    lazyListState: LazyListState,
    lazyGridState: LazyGridState,
    scrollState: ScrollState
) {
    val navigation: (String) -> Unit = { route ->
        if (route == "-1") {
            navController.popBackStack()
        } else {
            navController.navigate(route)
        }
    }
    navigation(
        startDestination = ScreenRoutes.MoreRoute,
        route = ScreenRoutes.MoreRouteNavHost
    ) {
        composable(
            route = ScreenRoutes.MoreRoute,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        500, easing = LinearEasing
                    )
                )
            }
        ) { entry ->
            MoreScreen(
                onItemClicked = {
                    navigation(it)
                },
                lazyListState
            )
        }
        composable(
            route = ScreenRoutes.BundleRoute,
        ) { entry ->
            BundlesScreen(
                onBackClicked = {
                    navigation(it)
                },
                state = lazyGridState
            )

        }
        composable(
            route = ScreenRoutes.RanksRoute
        ) { entry ->
            RanksScreen(
                onBackClicked = {
                    navigation(it)
                },
                scrollState = scrollState
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