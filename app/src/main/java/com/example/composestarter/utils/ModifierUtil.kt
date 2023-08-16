package com.example.composestarter.utils

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


fun Modifier.widthPercent(percent: Float, configuration: Configuration): Modifier {
    val screenWidth = configuration.screenWidthDp.dp
    return width(screenWidth * percent)
}


fun Modifier.heightPercent(percent: Float, configuration: Configuration): Modifier {
    val screenHeight = configuration.screenHeightDp.dp
    return height(screenHeight * percent)
}

fun Modifier.percent(
    configuration: Configuration,
    widthPercent: Float? = null,
    heightPercent: Float? = null
): Modifier {
    val modifier = this
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val newWidth = widthPercent?.let { screenWidth * widthPercent }
    val newHeight = heightPercent?.let { screenHeight * heightPercent }
    newWidth?.let { modifier.width(newWidth) }
    newHeight?.let { modifier.height(newHeight) }
    return modifier
}


enum class ButtonState { Pressed, Idle }

fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.90f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}