
package com.example.composestarter.customViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestarter.domain.model.weapons.ChromasItemUIModel
import com.example.composestarter.domain.model.weapons.SkinsItemUIModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkinsListItem(
    skin: ChromasItemUIModel,
    onSkinClicked: (String) -> Unit = {},
    onLongClicked: (ChromasItemUIModel) -> Unit
) {

    Column(modifier = Modifier.combinedClickable(
        onClick = {
            onSkinClicked(skin.streamedVideo ?: "")
        },
        onLongClick = {
            onLongClicked(
               skin
            )
        }
    )) {
        loadImage(
            url = skin.displayIcon.toString(),
            modifier = Modifier
                .clip(CircleShape)
                .width(82.dp)
                .height(82.dp)

        )
        Text(
            text = skin.displayName.toString(),
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


@Composable
fun SkinsItem(
    skin: SkinsItemUIModel,
    categoryTitle: String,
    onSkinClicked: (String) -> Unit,
    onLongClicked: (ChromasItemUIModel) -> Unit
) {

    Card(
        modifier = Modifier.padding(12.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Text(
            text = categoryTitle,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp)
        )
        Divider(modifier = Modifier.padding(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            itemsIndexed(skin.chromas!!) { index, skin ->
                Row(modifier = Modifier) {
                    if (index == 0) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    SkinsListItem(
                        skin = skin,
                        onSkinClicked = {
                            onSkinClicked(it)
                        },
                        onLongClicked = {
                            onLongClicked(it)
                        })
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }

}

