package com.example.composestarter.customViews

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun LoadingDialog(
    isShowingDialog: () -> Boolean,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false
) {
    if (isShowingDialog()) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            CircularProgressIndicator()
        }
    }
}
