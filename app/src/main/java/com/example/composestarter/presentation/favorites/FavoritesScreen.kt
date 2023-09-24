package com.example.composestarter.presentation.favorites

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composestarter.presentation.favorites.agents.FavoriteAgentsScreen
import com.example.composestarter.presentation.favorites.skins.FavoriteSkinsScreen
import com.example.composestarter.utils.MoreScreenModel
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun FavoritesScreen(
    scrollState: ScrollState,
    lazyListState: LazyListState
) {

    var tabIndex by remember {
        mutableIntStateOf(0)
    }
    val items = listOf(
        MoreScreenModel(
            name = "Agents",
            icon = Icons.Filled.AlarmOn,
            route = ScreenRoutes.FavoriteAgentsRoute
        ),
        MoreScreenModel(
            name = "Skins",
            icon = Icons.Filled.AlarmOff,
            route = ScreenRoutes.FavoriteSkinsRoute
        )
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(modifier = Modifier.padding(top = 8.dp), selectedTabIndex = tabIndex) {
            items.forEachIndexed { index, item ->
                Tab(text = { Text(text = item.name) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> FavoriteAgentsScreen(state = lazyListState)
            1 -> FavoriteSkinsScreen(scrollState = scrollState)
        }
    }
}

