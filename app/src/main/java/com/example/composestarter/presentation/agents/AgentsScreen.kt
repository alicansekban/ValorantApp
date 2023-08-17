package com.example.composestarter.presentation.agents

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.LoadingDialog
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.AgentsUIModel
import com.example.composestarter.utils.ScreenRoutes


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
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TopBarView(
                title = { "Agents" },
                showBackButton = { false },
                onBackClick = { },
            )

            if (agents.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(
                        items = agents,
                        key = { agents -> agents.uuid!! })
                    { value ->
                        NewsItem(value, openDetail = {
                            openDetail(
                                ScreenRoutes.AgentsDetailRoute.replace(
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
fun NewsItem(agents: AgentsUIModel, openDetail: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openDetail(agents.uuid.toString()) }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            loadImage(
                url = agents.fullPortrait ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = agents.displayName ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = agents.description ?: "",
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun loadImage(
    url: String, modifier: Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
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