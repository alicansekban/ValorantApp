package com.example.composestarter.customViews

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPlay(
    videoURL : String
) {
    Column(
        Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val mContext = LocalContext.current

        val exoPlayer = ExoPlayer.Builder(mContext).build()
        val mediaItem = MediaItem.fromUri(Uri.parse(videoURL))
        exoPlayer.setMediaItem(mediaItem)

        val playerView = StyledPlayerView(mContext)
        playerView.player = exoPlayer
        DisposableEffect(AndroidView(factory = {playerView})) {
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true

            onDispose {
                exoPlayer.release()
            }
        }
    }
}