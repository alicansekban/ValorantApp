package com.example.composestarter.graphs

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
            )
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