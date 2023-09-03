package com.example.composestarter.customViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestarter.domain.model.agents.AgentsUIModel
import com.example.composestarter.presentation.agents.loadImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgentInformationItem(
    agents: AgentsUIModel,
    onAgentClicked: (String, String) -> Unit,
    addAgentToFavorite: (String, String) -> Unit,
) {
    Column(modifier = Modifier.combinedClickable
        (
        onClick = {
            onAgentClicked(
                agents.uuid.toString(),
                agents.voiceLine?.mediaList?.firstOrNull { it?.wave != null }?.wave.toString()
            )
        },
        onLongClick = {
            addAgentToFavorite(
                agents.uuid.toString(),
                agents.voiceLine?.mediaList?.firstOrNull { it?.wave != null }?.wave.toString()
            )
        }

    )) {
        loadImage(
            url = agents.displayIcon.toString(),
            modifier = Modifier
                .clip(CircleShape)
                .width(72.dp)
                .height(72.dp)
        )
        Text(
            text = agents.displayName.toString(),
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

@Composable
fun AgentsItem(
    agents: List<AgentsUIModel>,
    roleTitle: String,
    roleIcon: String,
    onAgentClicked: (String, String) -> Unit,
    addAgentToFavorite: (String, String,AgentsUIModel) -> Unit,
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
                text = roleTitle,
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
                    AgentInformationItem(
                        ability,
                        onAgentClicked = { id, url ->
                            onAgentClicked(id, url)
                        },
                        addAgentToFavorite ={ id,voiceLine ->
                            addAgentToFavorite(id,voiceLine,ability)
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }

}



