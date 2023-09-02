package com.example.composestarter.presentation.favorites.agents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success

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

        }
    }
}