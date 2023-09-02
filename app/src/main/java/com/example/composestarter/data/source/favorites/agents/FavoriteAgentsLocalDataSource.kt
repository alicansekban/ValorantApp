package com.example.composestarter.data.source.favorites.agents

import com.example.composestarter.data.local.AppDatabase
import javax.inject.Inject

class FavoriteAgentsLocalDataSource @Inject constructor(
    private val db : AppDatabase
) {
}