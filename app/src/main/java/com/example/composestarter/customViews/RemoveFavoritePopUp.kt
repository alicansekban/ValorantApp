package com.example.composestarter.customViews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun RemoveFavoritePopUp(
    onDismissRequest: () -> Unit,
    removeFromFavorites: () -> Unit
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
                    text = "Are you sure you want to remove from your favorites?",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 12.dp),
                    textAlign = TextAlign.Center
                )


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Yes",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .clickable {
                                removeFromFavorites()
                            },
                        color = Color.Blue
                    )
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .clickable {
                                onDismissRequest()
                            },
                        color = MaterialTheme.colorScheme.error
                    )

                }

            }

        }

    }
}