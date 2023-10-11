package com.example.composestarter.graphs

import WeaponsScreen
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
import com.example.composestarter.presentation.weapons.detail.WeaponDetailScreen
import com.example.composestarter.utils.ScreenRoutes

fun NavGraphBuilder.weaponsNavGraph(
    navController: NavController,
    scrollState: ScrollState,
    lazyListState: LazyListState
) {
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
        composable(ScreenRoutes.WeaponsRoute,
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
            WeaponsScreen(
                openDetail = {
                    navigation(it)
                },
                scrollState = scrollState
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
                    },
                    lazyListState
                )
            }
        }
    }
}