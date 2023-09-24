package com.example.composestarter.presentation.ranks

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.caseapp.R
import com.example.composestarter.customViews.RanksItem
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.ranks.RanksUIModel

@Composable
fun RanksScreen(
    viewModel: RanksViewModel = hiltViewModel(),
    onBackClicked : (String) -> Unit = {},
    scrollState: ScrollState
) {

    val ranks by viewModel.ranks.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (ranks) {
        is Error -> {
            Toast.makeText(
                context,
                (ranks as Error<List<RanksUIModel>>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (ranks as Success<List<RanksUIModel>>).response

            StatelessRanksScreen(
                response,
                onBackClicked,
                scrollState
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessRanksScreen(
    ranks: List<RanksUIModel>,
    onBackClicked : (String) -> Unit = {},
    scrollState: ScrollState
) {

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {

            TopBarView(title = stringResource(R.string.ranks_title) , showBackButton = {
                true
            }) {
                onBackClicked("-1")
            }
            val groupedRanks =
                ranks[4].tiers?.filter { it.divisionName != "Unused1" && it.divisionName != "Unused2" && it.divisionName != "UNRANKED" }
                    ?.groupBy { it.divisionName }?.entries?.reversed()


            groupedRanks?.forEach { (division, ranksInDivisioon) ->

                RanksItem(ranks = ranksInDivisioon, categoryTitle = division.toString())
            }
        }
    }
}