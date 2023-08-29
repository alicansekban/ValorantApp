@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalGlideComposeApi::class
)

package com.example.composestarter.presentation.maps.detail

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.ImageFocusPopup
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.maps.MapsUIModel
import com.example.composestarter.presentation.agents.loadImage
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun MapsDetailScreen(
    onBackPressed: (String) -> Unit,
    viewModel: MapsDetailViewModel = hiltViewModel()
) {

    val mapDetail by viewModel.map.collectAsStateWithLifecycle()
    val context = LocalContext.current
    when (mapDetail) {
        is Error -> {
            Toast.makeText(
                context,
                (mapDetail as Error<MapsUIModel>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (mapDetail as Success<MapsUIModel>).response
            stateLessMapDetail(
                onBackPressed,
                response
            )
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
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
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
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
                        it.error(R.drawable.ic_placeholder)
                            .placeholder(R.drawable.ic_placeholder)
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

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Description : ${map.narrativeDescription ?: ""}",
                    style = MaterialTheme.typography.bodyLarge
                )


                Text(
                    text = "Minimap",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Divider(modifier = Modifier.padding(4.dp))
                loadImage(url = map.displayIcon.toString(), modifier = Modifier)
            }
        }
    }
}
