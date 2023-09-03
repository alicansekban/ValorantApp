package com.example.composestarter.presentation.favorites.agents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.data.local.model.agents.FavoriteAgentsEntity
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.presentation.agents.loadImage
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun FavoriteAgentsScreen(
    viewModel: FavoriteAgentsViewModel = hiltViewModel(),
    onBackClicked: (String) -> Unit
) {

    val agents by viewModel.favoriteAgents.collectAsStateWithLifecycle()
    when (agents) {
        is Error -> {

        }

        is Loading -> {

        }

        is Success -> {
            val response = (agents as Success<List<FavoriteAgentsEntity>>).response

            StatelessFavoriteAgentsScreen(response,onBackClicked)
        }
    }
}

@Composable
fun StatelessFavoriteAgentsScreen(
    agents: List<FavoriteAgentsEntity>,
    onBackClicked: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier
    ) {
        item {
            TopBarView(
                title = { "Favorite Agents" },
                showBackButton = { true },
                onBackClick = { onBackClicked(ScreenRoutes.FavoritesRoute)},
            )
        }
        item {
            agents
                .groupBy { it.role }
                .map { (role, agents) ->
                    val roleIcons = agents
                        .groupBy { it.roleDisplayIcon }
                        .map { (icon, items) ->
                            FavoriteAgentsItem(
                                role, icon, agents, onAgentClicked = {}
                            )
                        }
                }

        }
    }


}

@Composable
fun FavoriteAgentsItem(
    role: String,
    roleIcon: String,
    agents: List<FavoriteAgentsEntity>,
    onAgentClicked: () -> Unit,
) {

    Card(
        modifier = Modifier.padding(12.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            loadImage(
                url = roleIcon,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp),
                cardColor = MaterialTheme.colorScheme.error
            )

            Text(
                text = role,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            )

        }

        Divider(modifier = Modifier.padding(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            itemsIndexed(agents) { index, ability ->
                Row(modifier = Modifier) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    FavoriteAgentInformationItem(
                        ability,
                        onAgentClicked = { id, url ->
                            onAgentClicked()
                        },
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteAgentInformationItem(
    agent: FavoriteAgentsEntity,
    onAgentClicked: (String, String) -> Unit,
) {

    Column(modifier = Modifier.combinedClickable
        (
        onClick = {
            onAgentClicked(
                agent.id,
                agent.voiceLine
            )
        },
        onLongClick = {

        }

    )) {
        loadImage(
            url = agent.displayIcon,
            modifier = Modifier
                .clip(CircleShape)
                .width(72.dp)
                .height(72.dp)
        )
        Text(
            text = agent.displayName,
            fontSize = 11.sp,
            lineHeight = 13.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(72.dp)
                .padding(top = 6.dp),
        )
    }
}
