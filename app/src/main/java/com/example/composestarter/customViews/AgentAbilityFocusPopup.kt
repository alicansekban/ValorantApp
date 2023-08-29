

package com.example.composestarter.customViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.caseapp.R
import com.example.composestarter.domain.model.agents.AbilitiesUIModel
import com.example.composestarter.utils.heightPercent

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AgentSkillPopup(
    ability: AbilitiesUIModel,
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
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = ability.displayName.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 12.dp)
                )
                Card(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(100.dp)
                        .width(100.dp),
                ) {
                    GlideImage(
                        model = ability.displayIcon.toString(),
                        contentDescription = "loadImage",
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .height(100.dp)
                            .width(100.dp)
                    ) {
                        it.error(R.drawable.ic_placeholder)
                            .placeholder(R.drawable.ic_placeholder)
                            .load(ability.displayIcon.toString())

                    }
                }
                Text(
                    text = ability.description.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 12.dp)
                )


            }

        }

    }
}