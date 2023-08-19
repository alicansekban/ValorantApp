package com.example.composestarter.customViews

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composestarter.domain.model.AbilitiesUIModel
import com.example.composestarter.presentation.agents.loadImage

@Composable
fun AgentAbilitiesItem(
    ability : AbilitiesUIModel
) {
    Column {
        loadImage(
            url = ability.displayIcon.toString(),
            modifier = Modifier
                .clip(CircleShape)
                .width(72.dp)
                .height(72.dp)

        )
        Text(
            text = ability.displayName.toString(),
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
fun AgentAbilities(
    list : List<AbilitiesUIModel>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        itemsIndexed(list) { index, subCategory ->
            Row(modifier = Modifier) {
                if (index == 0) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
                AgentAbilitiesItem(subCategory)
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}