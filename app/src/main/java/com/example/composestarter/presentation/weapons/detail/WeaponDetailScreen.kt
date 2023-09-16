@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)

package com.example.composestarter.presentation.weapons.detail

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.customViews.FavoriteFirstTimeMessagePopUp
import com.example.composestarter.customViews.FocusPopUpSkin
import com.example.composestarter.customViews.SkinsItem
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.customViews.VideoPlay
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.weapons.ChromasItemUIModel
import com.example.composestarter.domain.model.weapons.WeaponsUIModel
import com.example.composestarter.utils.Constant

@Composable
fun WeaponDetailScreen(
    onBackPressed: (String) -> Unit,
    viewModel: WeaponDetailViewModel = hiltViewModel()
) {

    val weapon by viewModel.weapon.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var isDialogShown by remember {
        mutableStateOf(
            SPUtils.getInstance().getBoolean(Constant.IS_SKIN_FAVORITE_MESSAGE_SHOWED, false)
        )
    }
    if (!isDialogShown) {
        FavoriteFirstTimeMessagePopUp(
            onDismissRequest = {
                SPUtils.getInstance().put(Constant.IS_SKIN_FAVORITE_MESSAGE_SHOWED, true)
                isDialogShown = true

            },
            message = stringResource(R.string.first_time_skin_favorite_desc)
        )

    }

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
                onBackPressed,
                addToFavoriteClicked = { item, name ->
                    viewModel.addSkinToFavorite(item, name)
                }
            )
        }
    }

    val state by viewModel.favoriteSkin.collectAsStateWithLifecycle()
    when (state) {
        is Error -> {
            Toast.makeText(
                context,
                (state as Error<Any>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
            viewModel.favoriteEmitted()
        }

        is Loading -> {

        }

        is Success -> {

        }
    }

    val popupControl by remember { derivedStateOf { state is Success<*> } }
    if (popupControl) {
        viewModel.favoriteEmitted()
        Toast.makeText(
            context,
            stringResource(R.string.skin_added_to_favorites_success_message),
            Toast.LENGTH_SHORT
        ).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun StatelessWeaponDetail(
    weapon: WeaponsUIModel,
    onBackPressed: (String) -> Unit,
    addToFavoriteClicked: (ChromasItemUIModel, String) -> Unit
) {

    val context = LocalContext.current
    var previewUrl by remember {
        mutableStateOf("")
    }
    var isVideoShowable by remember {
        mutableStateOf(false)
    }
    var focusPopUp by remember {
        mutableStateOf(false)
    }

    var skinModel: MutableState<ChromasItemUIModel> = remember {
        mutableStateOf(value = weapon.skins!![0].chromas!![0])
    }
    if (focusPopUp) {
        FocusPopUpSkin(
            model = skinModel.value,
            onDismissRequest = { focusPopUp = false },
            watchtPreviewVideo = {
                if (!it.streamedVideo.isNullOrBlank()) {
                    isVideoShowable = true
                    previewUrl = it.streamedVideo
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.skin_preview_not_available_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            addToMyFavorite = {
                focusPopUp = false
                addToFavoriteClicked(it, weapon.displayName.toString())

            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .blur(if (focusPopUp) 15.dp else 0.dp)
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
                        title = stringResource(R.string.weapon_detail_title),
                        showBackButton = { true },
                        onBackClick = { onBackPressed("-1") })
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = weapon.displayName ?: "",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            GlideImage(
                                model = weapon.displayIcon.toString(),
                                contentDescription = "loadImage",
                                modifier = Modifier.height(150.dp)
                            ) {
                                it.error(R.drawable.ic_placeholder)
                                    .placeholder(R.drawable.ic_placeholder)
                                    .load(weapon.displayIcon.toString())

                            }
                            WeaponDetailStats(
                                weapon
                            )
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
                                previewUrl = it
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.skin_preview_not_available_message),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        onLongClicked = {
                            //  addToFavoriteClicked(it, weapon.displayName.toString())
                            focusPopUp = true
                            skinModel.value = it
                        }

                    )

                }

            }


        }

    }

    if (isVideoShowable) {
        showSkinPreview(mediaUrl = previewUrl) {
            isVideoShowable = false
        }
    }
}

@Composable
fun WeaponDetailStats(
    weapon: WeaponsUIModel
) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    ) {
        Text(text = "Weapon Stats", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Divider(modifier = Modifier.padding(end = 8.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "fireRate : ${weapon.weaponStats?.fireRate ?: ""}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "magazineSize : ${weapon.weaponStats?.magazineSize ?: ""}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "equipTime : ${weapon.weaponStats?.equipTimeSeconds ?: ""}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "reloadTime : ${weapon.weaponStats?.reloadTimeSeconds ?: ""}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "firstBulletAccuracy : ${weapon.weaponStats?.firstBulletAccuracy ?: ""}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun showSkinPreview(
    mediaUrl: String,
    onBackPressed: () -> Unit
) {

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            TopBarView(
                title = stringResource(R.string.skin_preview_title),
                showBackButton = { true }) {
                onBackPressed()
            }
            VideoPlay(videoURL = mediaUrl)
        }

    }

}
