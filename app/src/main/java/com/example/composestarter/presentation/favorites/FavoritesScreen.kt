package com.example.composestarter.presentation.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.presentation.more.MoreScreenItems
import com.example.composestarter.utils.MoreScreenModel
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun FavoritesScreen(
    onItemClicked: (String) -> Unit = {}
) {
    val items = listOf(
        MoreScreenModel(
            name = "Favorite Agents",
            icon = Icons.Filled.AlarmOn,
            route = ScreenRoutes.FavoriteAgentsRoute
        ),
        MoreScreenModel(
            name = "Favorite Skins",
            icon = Icons.Filled.AlarmOff,
            route = ScreenRoutes.FavoriteSkinsRoute
        ),

    )
    val state = rememberLazyListState()
    LazyColumn(modifier = Modifier.fillMaxSize(), state = state) {

        item {
            TopBarView(title = { "More Content" }, showBackButton = { false }, onBackClick = {})
        }

        items(
            items = items,
        ) { value ->

            MoreScreenItems(item = value, itemClicked = { onItemClicked(it) })
        }

    }
}

