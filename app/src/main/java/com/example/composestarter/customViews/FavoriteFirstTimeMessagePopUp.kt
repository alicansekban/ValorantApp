package com.example.composestarter.customViews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import com.example.composestarter.utils.heightPercent


@Composable
fun FavoriteFirstTimeMessagePopUp(
    onDismissRequest: () -> Unit,
    message : String
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
                .heightPercent(0.20f, configuration)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
            ) {

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Center
                )

                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth().padding(start = 24.dp)) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 12.dp).clickable { onDismissRequest() },
                        textAlign = TextAlign.Center,
                        color = Color.Blue
                    )
                }
            }

        }

    }
}