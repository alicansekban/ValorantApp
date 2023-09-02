package com.example.composestarter.presentation.bundles

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.bundles.BundlesUIModel
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun BundlesScreen(
    viewModel: BundlesViewModel = hiltViewModel(),
    onBackClicked: (String) -> Unit = {}
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
                response,
                onBackClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessBundlesScreen(
    bundles: List<BundlesUIModel>,
    onBackClicked: (String) -> Unit = {},
) {

    var searchQuery by remember { mutableStateOf("") }

    val filteredBundles = filterBundles(bundles, searchQuery)

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
                    TopBarView(title = { "Bundles" }, showBackButton = { true }) {
                        onBackClicked(ScreenRoutes.MoreRoute)
                    }
                }
                item(
                    span = {
                        GridItemSpan(maxCurrentLineSpan)
                    }
                ) {
                    OutlinedTextField(
                        value = searchQuery, onValueChange = {
                            searchQuery = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        shape = CircleShape,
                        placeholder = {
                            Text(text = "Search...")
                        },
                        maxLines = 1,
                        singleLine = true
                    )
                }

                items(
                    items = filteredBundles,
                    key = { bundle -> bundle.uuid!! }
                ) { value ->
                    BundleItem(value)
                }
            }
        }


    }

}
fun filterBundles(bundles: List<BundlesUIModel>, query: String): List<BundlesUIModel> {
    return if (query.isBlank()) {
        // Eğer sorgu boşsa, tüm öğeleri göster
        bundles
    } else {
        // Aksi halde sorguya göre öğeleri filtrele
        bundles.filter { bundle ->
            bundle.displayName?.contains(query, ignoreCase = true) == true
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
            .height(250.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = bundle.displayName ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            GlideImage(
                model = bundle.displayIcon.toString(),
                contentDescription = "Bundle Image",
                contentScale = ContentScale.Crop
            ) {
                it.error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .load(bundle.displayIcon)

            }
        }

    }
}
