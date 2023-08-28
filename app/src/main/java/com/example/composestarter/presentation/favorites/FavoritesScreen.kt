package com.example.composestarter.presentation.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun FavoritesScreen(
    onBackClicked: (String) -> Unit = {}
) {

    Column {

        TopBarView(title = { "Favorites" }, showBackButton = { true}) {
            onBackClicked(ScreenRoutes.MoreRoute)
        }
    }
}