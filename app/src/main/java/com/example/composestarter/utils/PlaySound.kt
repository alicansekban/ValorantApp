package com.example.composestarter.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import java.io.IOException

fun playSound(context: Context,url : String) {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
    )

    try {
        mediaPlayer.setDataSource(context, Uri.parse(url))
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { mp ->
            mp.start()
        }
        mediaPlayer.start()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}