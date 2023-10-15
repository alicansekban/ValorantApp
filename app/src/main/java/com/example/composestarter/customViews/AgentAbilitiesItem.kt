package com.example.composestarter.customViews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.caseapp.R
import com.example.composestarter.domain.model.agents.AbilitiesUIModel

@Composable
fun AgentAbilitiesItem(
    ability : AbilitiesUIModel,
    onAbilityClicked : () -> Unit
) {
    Column(modifier = Modifier.clickable {
        onAbilityClicked()
    }) {
        Card(
            modifier = Modifier
                .clip(CircleShape)
                .width(72.dp)
                .height(72.dp),
        ) {
            AsyncImage(
                model = ability.displayIcon.toString(),
                contentDescription = "loadImage",
                modifier = Modifier
                    .clip(CircleShape)
                    .width(72.dp)
                    .height(72.dp),
                placeholder = painterResource(id = R.drawable.ic_placeholder),
                error = painterResource(id = R.drawable.ic_placeholder),
            )
        }
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
