package com.example.composestarter.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.composestarter.presentation.agents.AgentsScreen
import com.example.composestarter.presentation.agents.detail.AgentDetailScreen
import com.example.composestarter.utils.ScreenRoutes

fun NavGraphBuilder.agentsNavGraph(navController: NavController, scrollState: ScrollState) {
    val navigation: (String) -> Unit = { route ->
        if (route == "-1") {
            navController.popBackStack()
        } else {
            navController.navigate(route)
        }
    }
    navigation(
        startDestination = ScreenRoutes.AgentsRoute,
        route = ScreenRoutes.AgentsRouteNavHost
    ) {
        composable(
            route = ScreenRoutes.AgentsRoute,
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
            }
        ) { entry ->

            AgentsScreen(
                openDetail = {
                    navigation(it)
                },
                scrollState = scrollState
            )
        }
        composable(
            ScreenRoutes.AgentsDetailRoute, arguments = listOf(
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
            }
        ) { entry ->
            val id = entry.arguments?.getString("id")
            if (id?.isNotEmpty() == true) {
                AgentDetailScreen(
                    onBackPressed = {
                        navigation(it)
                    },
                    scrollState
                )
            }
        }
    }
}