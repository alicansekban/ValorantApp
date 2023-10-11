package com.example.composestarter.graphs

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.composestarter.presentation.favorites.FavoritesScreen
import com.example.composestarter.utils.ScreenRoutes


fun NavGraphBuilder.favoritesNavGraph(
    lazyListState: LazyListState,
    scrollState: ScrollState
) {
    navigation(
        startDestination = ScreenRoutes.FavoritesRoute,
        route = ScreenRoutes.FavoritesRouteNavHost
    ) {
        composable(
            route = ScreenRoutes.FavoritesRoute,
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
            FavoritesScreen(
                scrollState, lazyListState
            )
        }
    }
}