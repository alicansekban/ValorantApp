package com.example.composestarter.presentation.agents.detail

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.customViews.AgentAbilitiesItem
import com.example.composestarter.customViews.AgentSkillPopup
import com.example.composestarter.customViews.ImageFocusPopup
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.agents.AgentsUIModel
import com.example.composestarter.presentation.agents.loadImage
import com.example.composestarter.utils.playSound

@Composable
fun AgentDetailScreen(
    onBackPressed: (String) -> Unit,
    viewModel: AgentDetailViewModel = hiltViewModel()
) {
    val agentData by viewModel.agent.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (agentData) {
        is Error -> {
            Toast.makeText(
                context,
                (agentData as Error<AgentsUIModel>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }
        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (agentData as Success<AgentsUIModel>).response
            StateLessAgentDetail(agent = response, onBackPressed = onBackPressed)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StateLessAgentDetail(
    agent: AgentsUIModel,
    onBackPressed: (String) -> Unit
) {
    var isAgentImageZoomable by remember { mutableStateOf(false) }
    var showYouTubePlayer by remember {
        mutableStateOf(false)
    }
    var isSkillPopUpClickable by remember {
        mutableStateOf(false)
    }
    var skillModelIndex by remember {
        mutableStateOf(0)
    }


    if (isSkillPopUpClickable) {
        AgentSkillPopup(agent.abilities[skillModelIndex] ) {
            isSkillPopUpClickable = false
        }
    }

    if (isAgentImageZoomable) {
        ImageFocusPopup(image = agent.fullPortrait.toString()) {
            isAgentImageZoomable = false
        }
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .blur(if (isAgentImageZoomable || isSkillPopUpClickable) 15.dp else 0.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarView(
            title = { agent.displayName.toString() },
            showBackButton = { true },
            onBackClick = { onBackPressed("-1") },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            loadImage(
                url = agent.fullPortrait ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .combinedClickable(onClick = {
                        playSound(
                            context,
                            agent.voiceLine?.mediaList?.firstOrNull { it?.wave != null }?.wave.toString()
                        )
                    }, onLongClick = {
                        showYouTubePlayer = true
                    }, onDoubleClick = {
                        isAgentImageZoomable = true
                    })
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Name : ${agent.displayName}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Role : ${agent.role?.displayName}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Description ;",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = agent.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )



        }
        Text(
            text = "Abilities",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )
        Divider(modifier = Modifier.padding(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            itemsIndexed(agent.abilities) { index, ability ->
                Row(modifier = Modifier) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    AgentAbilitiesItem(ability) {
                        skillModelIndex = index
                        isSkillPopUpClickable = true
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
    if (showYouTubePlayer) {
       // YoutubeScreen(videoId = "fKIw_j6xp4A", modifier = Modifier)
    }
}