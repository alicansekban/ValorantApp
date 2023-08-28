@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composestarter.presentation.agents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.AgentsItem
import com.example.composestarter.customViews.LoadingDialog
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


    when (agents) {
        is Error -> {

        }

        is Loading -> {
            LoadingDialog(isShowingDialog = { true })

        }

        is Success -> {
            LoadingDialog(isShowingDialog = { false })
            val response = (agents as Success<List<AgentsUIModel>>).response

            StatelessAgentsScreen(response, openDetail = openDetail)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessAgentsScreen(
    agents: List<AgentsUIModel>,
    openDetail: (String) -> Unit
) {

    val context = LocalContext.current
    Scaffold { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .verticalScroll(enabled = true, state = rememberScrollState())) {
            TopBarView(
                title = { "Agents" },
                showBackButton = { false },
                onBackClick = { },
            )
            if (agents.isNotEmpty()) {
                val groupedAgents = agents.sortedBy { it.role?.displayName }
                    .groupBy { it.role?.displayName }

                groupedAgents.forEach { (role, agentsInRole) ->

                    AgentsItem(agents = agentsInRole, roleTitle =role.toString(),onAgentClicked = { id, url ->
                        playSound(context,url)
                        openDetail(
                            ScreenRoutes.AgentsDetailRoute.replace(
                                oldValue = "{id}",
                                newValue = id
                            )
                        )
                    } )
                }
            }


        }

    }
}



@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun loadImage(
    url: String, modifier: Modifier,cardColor : Color = Color.White
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(cardColor),
    ) {
        GlideImage(
            model = url,
            contentDescription = "loadImage",
            modifier = modifier
        ) {
            it.error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .load(url)

        }
    }
}

// extension method for current page offset
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
