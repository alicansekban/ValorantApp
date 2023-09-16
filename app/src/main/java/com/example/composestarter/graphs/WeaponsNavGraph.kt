package com.example.composestarter.graphs

import WeaponsScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.composestarter.presentation.weapons.detail.WeaponDetailScreen
import com.example.composestarter.utils.ScreenRoutes

fun NavGraphBuilder.weaponsNavGraph(navController: NavController) {
    val navigation: (String) -> Unit = { route ->
        if (route == "-1") {
            navController.popBackStack()
        } else {
            navController.navigate(route)
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
        composable(
            ScreenRoutes.WeaponsDetailRoute, arguments = listOf(
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
}