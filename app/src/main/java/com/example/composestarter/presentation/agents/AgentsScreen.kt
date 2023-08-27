@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composestarter.presentation.agents

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.AgentsItem
import com.example.composestarter.customViews.DotsIndicator
import com.example.composestarter.customViews.LoadingDialog
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.AgentsUIModel
import com.example.composestarter.utils.ScreenRoutes
import kotlin.math.absoluteValue
import kotlin.math.min


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

                    AgentsItem(agents = agentsInRole, roleTitle =role.toString(),onAgentClicked = {
                        openDetail(
                            ScreenRoutes.AgentsDetailRoute.replace(
                                oldValue = "{id}",
                                newValue = it
                            )
                        )
                    } )
                }
            }


        }

    }
}


@Composable
fun AgentsItemOld(agents: AgentsUIModel, openDetail: (String) -> Unit, images: List<String?>?) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openDetail(agents.uuid.toString()) }
            .padding(8.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (images != null) {
                AgentsPager(images = images)
            }


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Name : ${agents.displayName}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Text(
                    text = "Role : ${agents.role?.displayName}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = agents.description ?: "",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
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

@Composable
fun AgentsPager(images: List<String?>) {
    val pagerState = rememberPagerState()
    HorizontalPager(
        pageCount = images.size,
        state = pagerState,
        pageSize = PageSize.Fill,
    ) { index ->

        val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
        val imageSize by animateFloatAsState(
            targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
            label = "",
            animationSpec = tween(durationMillis = 300)
        )
        Box(modifier = Modifier
            .graphicsLayer {
                val pageOffset =
                    pagerState.calculateCurrentOffsetForPage(index)
                val offScreenRight = pageOffset < 0f
                val deg = 105f
                val interpolated =
                    FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                rotationY =
                    min(
                        interpolated * if (offScreenRight) deg else -deg,
                        90f
                    )

                transformOrigin = TransformOrigin(
                    pivotFractionX = if (offScreenRight) 0f else 1f,
                    pivotFractionY = .5f
                )
            }
            .fillMaxSize()) {
            loadImage(
                url = images[index].toString(),
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .height(200.dp)
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    }
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            ) {
                DotsIndicator(
                    totalDots = images.size,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = Color.White,
                    unSelectedColor = Color.Black,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                )
            }

        }


    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun loadImage(
    url: String, modifier: Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White),
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
