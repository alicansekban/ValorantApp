package com.example.composestarter.customViews

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal const val ANIMATION_TIME = 1000L
internal const val DIALOG_BUILD_TIME = 300L

@Composable
internal fun AnimatedModalBottomSheetTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(ANIMATION_TIME.toInt()),
            initialOffsetY = { fullHeight -> fullHeight }
        ),
        exit = slideOutVertically(
            animationSpec = tween(ANIMATION_TIME.toInt()),
            targetOffsetY = { fullHeight -> fullHeight }
        ),
        content = content
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AnimatedScaleInTransition(
    visible: Boolean,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(ANIMATION_TIME.toInt())
        , transformOrigin = TransformOrigin(0.25f,0.5f)),
        exit = scaleOut(
            animationSpec = tween(ANIMATION_TIME.toInt())
        ),
        content = content
    )
}

@Composable
fun AnimatedTransitionDialog(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
    content: @Composable (AnimatedTransitionDialogHelper) -> Unit
) {
    val onDismissSharedFlow: MutableSharedFlow<Any> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            animateTrigger.value = true
        }
        launch {
            onDismissSharedFlow.asSharedFlow().collectLatest {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        }
    }

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        }
    ) {
        Box(contentAlignment = contentAlignment,
            modifier = Modifier
                .fillMaxSize()
        ) {
            AnimatedScaleInTransition(visible = animateTrigger.value,transformOrigin) {
                content(AnimatedTransitionDialogHelper(coroutineScope, onDismissSharedFlow))
            }
        }
    }
}

@Composable
fun AnimatedTransitionDialogNoAnimatedButtonDismiss(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateTrigger.value = true
        }
    }

    Dialog(
        onDismissRequest = {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger, onDismissRequest)
            }
        }
    ) {
        Box(contentAlignment = contentAlignment,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedScaleInTransition(visible = animateTrigger.value) {
                content()
            }
        }
    }
}


@Composable
fun AnimatedTransitionDialogEntryOnly(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    val animateTrigger = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateTrigger.value = true
        }
    }
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(contentAlignment = contentAlignment,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedScaleInTransition(visible = animateTrigger.value) {
                content()
            }
        }
    }
}

class AnimatedTransitionDialogHelper(
    private val coroutineScope: CoroutineScope,
    private val onDismissFlow: MutableSharedFlow<Any>) {

    fun triggerAnimatedDismiss() {
        coroutineScope.launch {
            onDismissFlow.emit(Any())
        }
    }
}

suspend fun startDismissWithExitAnimation(
    animateTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit
) {
    animateTrigger.value = false
    delay(ANIMATION_TIME)
    onDismissRequest()
}