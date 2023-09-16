package com.example.composestarter.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composestarter.presentation.bundles.BundlesScreen
import com.example.composestarter.presentation.more.MoreScreen
import com.example.composestarter.presentation.ranks.RanksScreen
import com.example.composestarter.presentation.seasons.SeasonsScreen
import com.example.composestarter.utils.ScreenRoutes

fun NavGraphBuilder.moreNavGraph(navController: NavController) {
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