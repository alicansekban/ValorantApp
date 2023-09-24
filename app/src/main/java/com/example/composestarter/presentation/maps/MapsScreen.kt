package com.example.composestarter.presentation.maps

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.caseapp.R
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.maps.MapsUIModel
import com.example.composestarter.presentation.agents.loadImage
import com.example.composestarter.utils.ScreenRoutes


@Composable
fun MapsScreen(
    viewModel: MapsViewModel = hiltViewModel(),
    scrollState: LazyListState,
    openDetail: (String) -> Unit

) {
    val maps by viewModel.maps.collectAsStateWithLifecycle()


    val context = LocalContext.current
    when (maps) {
        is Error -> {
            Toast.makeText(
                context,
                (maps as Error<List<MapsUIModel>>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (maps as Success<List<MapsUIModel>>).response
            StatelessMapScreen(
                response,
                scrollState,
                openDetail,
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessMapScreen(
    maps: List<MapsUIModel>,
    scrollState: LazyListState,
    openDetail: (String) -> Unit
) {

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (maps.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = scrollState
                ) {
                    item {
                        TopBarView(
                            title = stringResource(R.string.maps_title),
                            showBackButton = { false },
                            onBackClick = { },
                        )
                    }
                    items(
                        items = maps,
                        key = { agents -> agents.uuid!! })
                    { value ->

                        MapsItem(maps = value, openDetail = {
                            openDetail(
                                ScreenRoutes.MapsDetailRoute.replace(
                                    oldValue = "{id}",
                                    newValue = it
                                )
                            )
                        }
                        )
                    }
                }
            }
        }

    }

}


@Composable
fun MapsItem(maps: MapsUIModel, openDetail: (String) -> Unit) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openDetail(maps.uuid.toString()) }
            .padding(16.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            loadImage(
                url = maps.splash.toString(), modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Name : ${maps.displayName}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Coordinate : ${maps.coordinates ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))



            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
