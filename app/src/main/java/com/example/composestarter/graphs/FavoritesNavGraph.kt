package com.example.composestarter.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
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
            FavoritesScreen(
                scrollState, lazyListState
            )
        }
    }
}