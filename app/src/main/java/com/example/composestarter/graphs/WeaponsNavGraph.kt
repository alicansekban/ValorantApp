package com.example.composestarter.graphs

import WeaponsScreen
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
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
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
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
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }) { entry ->
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