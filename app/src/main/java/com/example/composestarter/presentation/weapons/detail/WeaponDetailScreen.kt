@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)

package com.example.composestarter.presentation.weapons.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.SkinsItem
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.WeaponsUIModel
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun WeaponDetailScreen(
    onBackPressed: (String) -> Unit,
    viewModel: WeaponDetailViewModel = hiltViewModel()
) {

    val weapon by viewModel.weapon.collectAsStateWithLifecycle()


    when (weapon) {
        is Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (weapon as Error<WeaponsUIModel>).errorMessage)
            }
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (weapon as Success<WeaponsUIModel>).response

            StatelessWeaponDetail(
                response,
                onBackPressed
            )
        }
    }

    //  VideoPlay(videoURL = "https://valorant.dyn.riotcdn.net/x/videos/release-07.03/9520c794-4614-636d-8b87-0d94cd5af43a_default_universal.mp4")
}

@Composable
fun StatelessWeaponDetail(
    weapon: WeaponsUIModel,
    onBackPressed: (String) -> Unit
) {

    val context = LocalContext.current
    var isVideoShowable by remember {
        mutableStateOf(false)
    }
    if (isVideoShowable) {
        // TODO : show popup for video
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            val state = rememberLazyListState()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = state
            ) {

                item {
                    TopBarView(
                        title = { weapon.displayName ?: "Weapon Detail" },
                        showBackButton = { true },
                        onBackClick = { onBackPressed(ScreenRoutes.WeaponsRoute) })
                }
                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        GlideImage(
                            model = weapon.displayIcon.toString(),
                            contentDescription = "loadImage",
                            modifier = Modifier.fillMaxSize()
                        ) {
                            it.error(R.drawable.ic_launcher_foreground)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .load(weapon.displayIcon.toString())

                        }
                    }
                }
                items(
                    items = weapon.skins!!,
                    key = { agents -> agents.uuid!! })
                { value ->

                    SkinsItem(
                        skin = value,
                        categoryTitle = value.displayName.toString(),
                        onSkinClicked = {
                            if (!it.isNullOrBlank()) {
                                isVideoShowable = true
                            } else {
                                Toast.makeText(
                                    context,
                                    "Video preview is not available for this skin",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })

                }

            }


        }

    }


}
