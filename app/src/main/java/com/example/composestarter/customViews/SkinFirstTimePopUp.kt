@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.composestarter.customViews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.composestarter.utils.heightPercent

@Composable
fun SkinFirstTimePopUp(
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
                .heightPercent(0.50f, configuration)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "deneme", modifier = Modifier.clickable { onDismissRequest() })
                }
            }

        }

    }
}