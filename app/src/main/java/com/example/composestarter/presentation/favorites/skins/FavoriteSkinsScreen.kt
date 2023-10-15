package com.example.composestarter.presentation.favorites.skins

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.blankj.utilcode.util.SPUtils
import com.example.caseapp.R
import com.example.composestarter.customViews.FavoriteFirstTimeMessagePopUp
import com.example.composestarter.customViews.RemoveFavoritePopUp
import com.example.composestarter.customViews.loadImage
import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.presentation.weapons.detail.showSkinPreview
import com.example.composestarter.utils.Constant

@Composable
fun FavoriteSkinsScreen(
    viewModel: FavoriteSkinsViewModel = hiltViewModel(),
    scrollState: ScrollState
) {

    val favoriteSkins by viewModel.favoriteSkins.collectAsStateWithLifecycle()
    val searchQuery: MutableState<String> = remember { mutableStateOf("") }
    LaunchedEffect(key1 = searchQuery.value) {
        if (searchQuery.value.length > 3) {
            viewModel.getFavoriteSkins(searchQuery.value)
        } else {
            viewModel.getFavoriteSkins("")
        }
    }

    var isDialogShown by remember {
        mutableStateOf(
            SPUtils.getInstance().getBoolean(Constant.IS_SKIN_FAVORITE_REMOVE_MESSAGE_SHOWED, false)
        )
    }
    if (!isDialogShown) {
        FavoriteFirstTimeMessagePopUp(
            onDismissRequest = {
                SPUtils.getInstance().put(Constant.IS_SKIN_FAVORITE_REMOVE_MESSAGE_SHOWED, true)
                isDialogShown = true

            },
            message = stringResource(R.string.remove_skin_from_favorite_message)
        )

    }

    val context = LocalContext.current
    when (favoriteSkins) {
        is Error -> {
            Toast.makeText(
                context,
                (favoriteSkins as Error<List<FavoriteSkinsEntity>>).errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }

        is Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            val response = (favoriteSkins as Success<List<FavoriteSkinsEntity>>).response
            if (response.isEmpty()) {
                EmptyScreen()
            } else {
                StatelessSkinsScreen(
                    response,
                    searchQuery = searchQuery.value,
                    onSearchQueryChange = { newValue ->
                        searchQuery.value = newValue
                    },
                    removeFavoriteClicked = {
                        viewModel.removeFavoriteSkin(it)
                    },
                    scrollState
                )
            }
        }
    }
    val removeSkin by viewModel.removeSkin.collectAsStateWithLifecycle()

    val popupControl by remember { derivedStateOf { removeSkin is Success<*> } }
    if (popupControl) {
        viewModel.removeFavoriteEmitted()
        Toast.makeText(
            context,
            stringResource(R.string.skin_removed_from_your_favorites_message),
            Toast.LENGTH_SHORT
        ).show()
        searchQuery.value = ""
        viewModel.getFavoriteSkins()
    }
}

@Composable
fun EmptyScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "You don't have any favorites yet.")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessSkinsScreen(
    skins: List<FavoriteSkinsEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    removeFavoriteClicked: (String) -> Unit,
    scrollState: ScrollState
) {

    val groupedSkins = skins.groupBy { it.weaponName }

    var previewUrl by remember {
        mutableStateOf("")
    }
    var isVideoShowable by remember {
        mutableStateOf(false)
    }
    var popupControl by remember {
        mutableStateOf(false)
    }
    var removeSkinId by remember {
        mutableStateOf("")
    }
    if (popupControl) {
        RemoveFavoritePopUp(onDismissRequest = { popupControl = false }, removeFromFavorites = {
            popupControl = false
            removeFavoriteClicked(removeSkinId)
        }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .blur(if (popupControl) 15.dp else 0.dp)
    ) {
        OutlinedTextField(
            value = searchQuery, onValueChange = {
                onSearchQueryChange(it)

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
        Spacer(modifier = Modifier.height(8.dp))
        groupedSkins.forEach { (weaponName, skins) ->
            FavoriteSkinsItem(
                skin = skins,
                weaponName = weaponName,
                removeFavoriteClicked = { id ->
                    removeSkinId = id
                    popupControl = true

                },
                onSkinClicked = {
                    previewUrl = it
                    isVideoShowable = true
                })
        }
    }
    if (isVideoShowable) {
        showSkinPreview(mediaUrl = previewUrl) {
            isVideoShowable = false
        }
    }
}


@Composable
fun FavoriteSkinsItem(
    skin: List<FavoriteSkinsEntity>,
    weaponName: String,
    removeFavoriteClicked: (String) -> Unit,
    onSkinClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier.padding(12.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Text(
            text = weaponName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )
        Divider(modifier = Modifier.padding(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            itemsIndexed(skin) { index, skin ->
                Row(modifier = Modifier) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    FavoriteSkinsListItem(
                        skin = skin,
                        removeFavoriteClicked = {
                            removeFavoriteClicked(it)
                        },
                        onSkinClicked
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteSkinsListItem(
    skin: FavoriteSkinsEntity,
    removeFavoriteClicked: (String) -> Unit,
    onSkinClicked: (String) -> Unit
) {

    Column(modifier = Modifier.combinedClickable(
        onClick = {
            onSkinClicked(skin.video)
        },
        onLongClick = {
            removeFavoriteClicked(skin.id)
        }
    )) {
        loadImage(
            url = skin.displayIcon,
            modifier = Modifier
                .clip(CircleShape)
                .width(82.dp)
                .height(82.dp)
        )
        Text(
            text = skin.displaySkinName,
            fontSize = 11.sp,
            lineHeight = 13.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(72.dp)
                .padding(top = 6.dp),
        )
    }
}
