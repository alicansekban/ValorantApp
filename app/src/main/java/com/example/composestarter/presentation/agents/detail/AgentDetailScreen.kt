package com.example.composestarter.presentation.agents.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.customViews.AgentAbilities
import com.example.composestarter.customViews.LoadingDialog
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.AgentsUIModel
import com.example.composestarter.presentation.agents.loadImage

@Composable
fun AgentDetailScreen(
    onBackPressed: (String) -> Unit,
    viewModel: AgentDetailViewModel = hiltViewModel()
) {
    val agentData by viewModel.agent.collectAsStateWithLifecycle()

    when (agentData) {
        is Error -> {}
        is Loading -> {
            LoadingDialog(isShowingDialog = { true })
        }

        is Success -> {
            LoadingDialog(isShowingDialog = { false })
            val response = (agentData as Success<AgentsUIModel>).response
            StateLessAgentDetail(agent = response, onBackPressed = onBackPressed)
        }
    }
}

@Composable
fun StateLessAgentDetail(
    agent: AgentsUIModel,
    onBackPressed: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarView(
            title = { agent.displayName.toString() },
            showBackButton = { true },
            onBackClick = { onBackPressed("-1") },
        )
        Text(text = "Abilities", modifier = Modifier.padding(start = 16.dp, top = 16.dp))
        AgentAbilities(list = agent.abilities)

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
                    .background(MaterialTheme.colorScheme.secondary)
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
                text = "Description :",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = agent.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Abilities Explained : ",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

        }
    }
}