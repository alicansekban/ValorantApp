@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalGlideComposeApi::class
)

package com.example.composestarter.presentation.maps.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.ImageFocusPopup
import com.example.composestarter.customViews.LoadingDialog
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.maps.MapsUIModel
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
    map: MapsUIModel
) {
    var isMapImageZoomable by remember { mutableStateOf(false) }

    if (isMapImageZoomable) {
        ImageFocusPopup(image = map.splash.toString()) {
            isMapImageZoomable = false
        }
    }

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)
            .blur(if (isMapImageZoomable) 15.dp else 0.dp)
        ) {
            TopBarView(
                title = { "Map Detail" },
                showBackButton = { true },
                onBackClick = { onBackPressed(ScreenRoutes.MapsRoute) },
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
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
                ) {
                    GlideImage(
                        model = map.splash,
                        contentDescription = "loadImage",
                        contentScale = ContentScale.FillBounds
                    ) {
                        it.error(R.drawable.ic_launcher_foreground)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .load(map.splash)

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Name : ${map.displayName}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Coordinates : ${map.coordinates}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))



            }
        }
    }
}
