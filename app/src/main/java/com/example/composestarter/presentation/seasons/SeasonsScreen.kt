package com.example.composestarter.presentation.seasons

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.seasons.SeasonsUIModel
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun SeasonsScreen(
    viewModel: SeasonsViewModel = hiltViewModel(),
    onBackClicked: (String) -> Unit = {}
) {

    val seasons by viewModel.seasons.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (seasons) {
        is Error -> {
            Toast.makeText(
                context,
                (seasons as Error<List<SeasonsUIModel>>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (seasons as Success<List<SeasonsUIModel>>).response
            StatelessSeasonsScreen(
                response,
                onBackClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessSeasonsScreen(
    response: List<SeasonsUIModel>,
    onBackClicked: (String) -> Unit = {}
) {

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TopBarView(
                title = { "Competitive Seasons" },
                showBackButton = { true },
                onBackClick = { onBackClicked(ScreenRoutes.MoreRoute) })
        }

    }
}
