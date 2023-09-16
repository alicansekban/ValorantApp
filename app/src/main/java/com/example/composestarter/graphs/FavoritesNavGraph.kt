package com.example.composestarter.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composestarter.presentation.favorites.FavoritesScreen
import com.example.composestarter.presentation.favorites.agents.FavoriteAgentsScreen
import com.example.composestarter.presentation.favorites.skins.FavoriteSkinsScreen
import com.example.composestarter.utils.ScreenRoutes


fun NavGraphBuilder.favoritesNavGraph(navController: NavController) {
    val navigation: (String) -> Unit = { route ->
        if (route == "-1") {
            navController.popBackStack()
        } else {
            navController.navigate(route)
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
}