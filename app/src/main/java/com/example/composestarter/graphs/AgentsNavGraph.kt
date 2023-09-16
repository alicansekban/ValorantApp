package com.example.composestarter.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.composestarter.presentation.agents.AgentsScreen
import com.example.composestarter.presentation.agents.detail.AgentDetailScreen
import com.example.composestarter.utils.ScreenRoutes

fun NavGraphBuilder.agentsNavGraph(navController: NavController) {
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
            route = ScreenRoutes.AgentsRoute
        ) { entry ->

            AgentsScreen(openDetail = {
                navigation(it)
            })
        }
        composable(
            ScreenRoutes.AgentsDetailRoute, arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )) { entry ->
            val id = entry.arguments?.getString("id")
            if (id?.isNotEmpty() == true) {
                AgentDetailScreen(
                    onBackPressed = {
                        navigation(it)
                    }
                )
            }
        }
    }
}