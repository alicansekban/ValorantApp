package com.example.composestarter.presentation.favorites.skins

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.presentation.agents.loadImage
import com.example.composestarter.utils.ScreenRoutes

@Composable
fun FavoriteSkinsScreen(
    viewModel: FavoriteSkinsViewModel = hiltViewModel(),
    onBackClicked: (String) -> Unit
) {

    val favoriteSkins by viewModel.favoriteSkins.collectAsStateWithLifecycle()
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
            StatelessSkinsScreen(
                response,
                onBackClicked
            )


        }
    }
}

@Composable
fun StatelessSkinsScreen(
    skins: List<FavoriteSkinsEntity>,
    onBackClicked: (String) -> Unit
) {

    val groupedSkins = skins.groupBy { it.weaponName }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopBarView(title = { "Favorite Skins" }, showBackButton = { true }) {
            onBackClicked(ScreenRoutes.FavoritesRoute)
        }
        groupedSkins.forEach { (weaponName, skins) ->
            FavoriteSkinsItem(skin = skins, weaponName = weaponName)
        }
    }
}


@Composable
fun FavoriteSkinsItem(
    skin: List<FavoriteSkinsEntity>,
    weaponName: String,
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
                FavoriteSkinsListItem(skin = skin)
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}


@Composable
fun FavoriteSkinsListItem(
    skin: FavoriteSkinsEntity
) {

    Column(modifier = Modifier) {
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
