@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.composestarter.customViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.utils.heightPercent

@Composable
fun ImageFocusPopup(
    image: String,
    onDismissRequest: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    AnimatedTransitionDialog(onDismissRequest = {
        onDismissRequest()
    }
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.15f)

        Surface(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .heightPercent(0.70f, configuration)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
            ) {

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(10.dp),
                ) {
                    GlideImage(
                        model =image,
                        contentDescription = "loadImage",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    ) {
                        it.error(R.drawable.ic_placeholder)
                            .placeholder(R.drawable.ic_placeholder)
                            .load(image)

                    }
                }

            }

        }

    }
}