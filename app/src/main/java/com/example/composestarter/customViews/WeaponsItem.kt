package com.example.composestarter.customViews

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestarter.domain.model.weapons.WeaponsUIModel
import com.example.composestarter.presentation.agents.loadImage

@Composable
fun WeaponInformationItem(
    weapons : WeaponsUIModel,
    onWeaponClicked : (String) -> Unit
) {
    Column(modifier = Modifier.clickable {
        onWeaponClicked(weapons.uuid.toString())
    }) {
        loadImage(
            url = weapons.displayIcon.toString(),
            modifier = Modifier
                .clip(CircleShape)
                .width(82.dp)
                .height(82.dp)

        )
        Text(
            text = weapons.displayName.toString(),
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
fun WeaponsItem(
    weapons: List<WeaponsUIModel>,
    categoryTitle : String,
    onWeaponClicked: (String) -> Unit
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
        itemsIndexed(weapons) { index, weapon ->
            Row(modifier = Modifier) {
                if (index == 0) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
                WeaponInformationItem(weapon) {
                    onWeaponClicked(it)
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}



