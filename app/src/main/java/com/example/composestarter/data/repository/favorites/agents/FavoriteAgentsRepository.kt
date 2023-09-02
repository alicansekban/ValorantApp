package com.example.composestarter.data.repository.favorites.agents

import com.example.composestarter.data.source.favorites.agents.FavoriteAgentsLocalDataSource
import javax.inject.Inject

class FavoriteAgentsRepository @Inject constructor(
    private val dataSource: FavoriteAgentsLocalDataSource
) {
}