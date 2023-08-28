package com.example.composestarter.presentation.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.utils.MoreScreenModel
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun MoreScreen(
    onItemClicked: (String) -> Unit = {}
) {

    val items = listOf(
        MoreScreenModel(
            name = "Ranks",
            icon = Icons.Filled.AlarmOn,
            route = ScreenRoutes.RanksRoute
        ),
        MoreScreenModel(
            name = "Bundles",
            icon = Icons.Filled.AlarmOff,
            route = ScreenRoutes.BundleRoute
        ),
        MoreScreenModel(
            name = "Competitive Seasons",
            icon = Icons.Filled.Air,
            route = ScreenRoutes.SeasonsRoute
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

@Composable
fun MoreScreenItems(item: MoreScreenModel, itemClicked: (String) -> Unit = {}) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp)
            .clickable {
                itemClicked(item.route)
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = "",
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

}