@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composestarter.presentation.bundles

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.bundles.BundlesUIModel

@Composable
fun BundlesScreen(
    viewModel: BundlesViewModel = hiltViewModel()
) {

    val bundles by viewModel.bundles.collectAsStateWithLifecycle()
    val context = LocalContext.current
    when (bundles) {
        is Error -> {
            Toast.makeText(
                context,
                (bundles as Error<List<BundlesUIModel>>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (bundles as Success<List<BundlesUIModel>>).response

            StatelessBundlesScreen(
                response
            )
        }
    }
}

@Composable
fun StatelessBundlesScreen(
    bundles: List<BundlesUIModel>
) {

    Scaffold { paddingValues ->

        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item(
                    span = {
                        GridItemSpan(maxCurrentLineSpan)
                    }
                ) {
                    TopBarView(title = { "Bundles" }, showBackButton = { false }) {
                    }
                }

                items(
                    items = bundles,
                    key = { bundle -> bundle.uuid!! }
                ) { value ->
                    BundleItem(value)
                }
            }
        }


    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BundleItem(
    bundle: BundlesUIModel
) {

    Card(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = bundle.displayName ?: "")
            Spacer(modifier = Modifier.height(8.dp))
            GlideImage(
                model = bundle.displayIcon.toString(),
                contentDescription = "Bundle Image",
                contentScale = ContentScale.Crop
            )
        }

    }
}
