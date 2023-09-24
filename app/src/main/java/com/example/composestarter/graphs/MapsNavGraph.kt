package com.example.composestarter.graphs

import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.composestarter.presentation.maps.MapsScreen
import com.example.composestarter.presentation.maps.detail.MapsDetailScreen
import com.example.composestarter.utils.ScreenRoutes

fun NavGraphBuilder.mapsNavGraph(
    navController: NavController,
    scrollState: LazyListState,
) {
    val navigation: (String) -> Unit = { route ->
        if (route == "-1") {
            navController.popBackStack()
        } else {
            navController.navigate(route)
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
                },
                scrollState = scrollState
            )
        }
        composable(
            ScreenRoutes.MapsDetailRoute, arguments = listOf(
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
}