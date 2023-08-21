package com.example.composestarter.presentation.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun MapsScreen(
    viewModel: MapsViewModel = hiltViewModel()
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Maps Screen")
    }
}