@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composestarter.presentation.agents

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.AgentsItem
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.agents.AgentsUIModel
import com.example.composestarter.utils.ScreenRoutes
import com.example.composestarter.utils.playSound


@Composable
fun AgentsScreen(
    viewModel: AgentsViewModel = hiltViewModel(),
    openDetail: (String) -> Unit
) {

    val agents by viewModel.agents.collectAsStateWithLifecycle()

    val context = LocalContext.current
    when (agents) {
        is Error -> {
            Toast.makeText(
                context,
                (agents as Error<List<AgentsUIModel>>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (agents as Success<List<AgentsUIModel>>).response
            StatelessAgentsScreen(
                response,
                openDetail = openDetail,
                addAgentToFavorite = { id, voiceLine, model ->
                    viewModel.addAgentToFavorite(model, id, voiceLine)
                })
        }
    }
    val favoriteState by viewModel.favoriteAgent.collectAsStateWithLifecycle()


    val popupControl by remember { derivedStateOf { favoriteState is Success<*> } }
    if (popupControl) {
        viewModel.favoriteEmitted()
        Toast.makeText(
            context,
            "Agent added to your favorites",
            Toast.LENGTH_LONG
        ).show()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessAgentsScreen(
    agents: List<AgentsUIModel>,
    openDetail: (String) -> Unit,
    addAgentToFavorite: (String, String, AgentsUIModel) -> Unit
) {

    val context = LocalContext.current
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(enabled = true, state = rememberScrollState())
        ) {
            TopBarView(
                title = { "Agents" },
                showBackButton = { false },
                onBackClick = { },
            )
            if (agents.isNotEmpty()) {
                 agents.sortedBy { it.role?.displayName }
                    .groupBy { it.role?.displayName }
                    .map { (role, agentsInRole) ->
                        val roleIcons = agentsInRole
                            .groupBy { it.role?.displayIcon }
                            .map { (icon, items) ->
                                AgentsItem(
                                    agents = items,
                                    roleTitle = role.toString(),
                                    roleIcon = icon.toString(),
                                    onAgentClicked = { id, url ->
                                        playSound(context, url)
                                        openDetail(
                                            ScreenRoutes.AgentsDetailRoute.replace(
                                                oldValue = "{id}",
                                                newValue = id
                                            )
                                        )
                                    },
                                    addAgentToFavorite = { id, voiceLine, model ->
                                        addAgentToFavorite(
                                            id, voiceLine, model
                                        )
                                    }
                                )
                            }
                        roleIcons
                    }
            }


        }

    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun loadImage(
    url: String, modifier: Modifier, cardColor: Color = Color.White
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(cardColor),
    ) {
        GlideImage(
            model = url,
            contentDescription = "loadImage",
            modifier = modifier
        ) {
            it.error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .load(url)

        }
    }
}
