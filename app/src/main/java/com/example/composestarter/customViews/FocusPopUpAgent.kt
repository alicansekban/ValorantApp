package com.example.composestarter.customViews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.example.caseapp.R
import com.example.composestarter.domain.model.agents.AgentsUIModel
import com.example.composestarter.presentation.agents.loadImage
import com.example.composestarter.utils.bounceClick
import com.example.composestarter.utils.heightPercent
import com.example.composestarter.utils.widthPercent

@Composable
fun FocusPopUpAgent(
    model: AgentsUIModel,
    onDismissRequest: () -> Unit,
    goToDetail: (AgentsUIModel) -> Unit,
    addToMyFavorite: (AgentsUIModel) -> Unit,
) {

    val configuration = LocalConfiguration.current
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {

        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.15f)
        Surface(
            shape = RoundedCornerShape(5.dp),
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier
                    .heightPercent(0.1f, configuration)
                    .clickable {
                        onDismissRequest()
                    })
                Card(
                    modifier = Modifier
                        .shadow(2.dp)
                        .fillMaxWidth()
                        .heightPercent(0.58f, configuration),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                ) {
                    loadImage(
                        url = model.fullPortrait.toString(),
                        modifier = Modifier.heightPercent(0.50f,configuration),
                        cardColor = MaterialTheme.colorScheme.background
                    )
                }

                Spacer(modifier = Modifier
                    .height(15.dp)
                    .clickable {
                        onDismissRequest()
                    })
                Card(
                    modifier = Modifier
                        .height(132.dp)
                        .widthPercent(0.72f, configuration),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Column {
                        Row(
                            Modifier
                                .weight(1f)
                                .height(44.dp)
                                .bounceClick()
                                .clickable {
                                    onDismissRequest()
                                    goToDetail(model)
                                }) {
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = stringResource(id = R.string.go_to_detail),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(5f),
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = FontFamily.Default,
                                fontSize = 16.sp
                            )
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "Go to detail icon",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Divider(Modifier.fillMaxWidth())
                        Row(
                            Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clickable {
                                    onDismissRequest()
                                    addToMyFavorite(model)
                                }) {
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = stringResource(id = R.string.add_to_my_favorites),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(5f),
                                color = MaterialTheme.colorScheme.tertiary,
                                fontFamily = FontFamily.Default,
                                fontSize = 16.sp
                            )
                            Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Add to my favorites icon",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Divider(Modifier.fillMaxWidth())

                    }
                }

                Spacer(modifier = Modifier
                    .heightPercent(0.1f, configuration)
                    .clickable {
                        onDismissRequest()
                    })
            }
        }
    }
}