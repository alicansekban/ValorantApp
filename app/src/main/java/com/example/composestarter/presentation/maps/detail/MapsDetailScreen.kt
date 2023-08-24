@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.composestarter.presentation.maps.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.customViews.LoadingDialog
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.MapsUIModel
import com.example.composestarter.presentation.agents.loadImage
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun MapsDetailScreen(
    onBackPressed: (String) -> Unit,
    viewModel: MapsDetailViewModel = hiltViewModel()
) {

    val mapDetail by viewModel.map.collectAsStateWithLifecycle()

    when (mapDetail) {
        is Error -> {}
        is Loading -> {
            LoadingDialog(isShowingDialog = { true })
        }

        is Success -> {
            val response = (mapDetail as Success<MapsUIModel>).response
            LoadingDialog(isShowingDialog = { false })
            stateLessMapDetail(
                onBackPressed,
                response
            )
        }
    }


}
@Composable
fun stateLessMapDetail(
    onBackPressed: (String) -> Unit,
    map : MapsUIModel
) {
    var isMapImageZoomable by remember { mutableStateOf(false) }

    if (isMapImageZoomable) {

    }

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TopBarView(
                title = { "Map Detail" },
                showBackButton = { true },
                onBackClick = { onBackPressed(ScreenRoutes.MapsRoute) },
            )

            loadImage(
                url = map.splash ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.secondary)
                    .combinedClickable(onClick = {
                    }, onLongClick = {
                        isMapImageZoomable = true
                    }, onDoubleClick = {
                        isMapImageZoomable = true
                    })
            )
        }
    }
}
