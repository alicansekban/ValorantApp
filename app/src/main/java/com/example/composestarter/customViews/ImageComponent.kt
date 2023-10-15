package com.example.composestarter.customViews

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.caseapp.R


@Composable
fun loadImage(
    url: String, modifier: Modifier, cardColor: Color = Color.White
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(cardColor),
    ) {
        AsyncImage(
            model = url,
            contentDescription = "loadImage",
            modifier = modifier,
            placeholder = painterResource(id = R.drawable.ic_placeholder),
            error = painterResource(id = R.drawable.ic_placeholder)
        )
    }
}