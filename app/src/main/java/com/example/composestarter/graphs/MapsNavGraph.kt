package com.example.composestarter.graphs

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
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
    lazyScrollState: LazyListState,
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
        startDestination = ScreenRoutes.MapsRoute,
        route = ScreenRoutes.MapsRouteNavHost
    ) {
        composable(ScreenRoutes.MapsRoute,
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
            }) {
            MapsScreen(
                openDetail = {
                    navigation(it)
                },
                scrollState = lazyScrollState
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
                    },
                    scrollState = scrollState
                )
            }
        }
    }
}