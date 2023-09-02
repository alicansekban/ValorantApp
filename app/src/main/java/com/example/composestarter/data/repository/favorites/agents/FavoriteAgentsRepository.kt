package com.example.composestarter.data.repository.favorites.agents

import com.example.composestarter.data.local.model.agents.FavoriteAgentsEntity
import com.example.composestarter.data.source.favorites.agents.FavoriteAgentsLocalDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class FavoriteAgentsRepository @Inject constructor(
    private val dataSource: FavoriteAgentsLocalDataSource
) {

    suspend fun insertFavoriteAgent(skin: FavoriteAgentsEntity): ResultWrapper<Any> {
        return dataSource.insertFavoriteAgent(skin)
    }

    fun getFavoriteAgents(): ResultWrapper<List<FavoriteAgentsEntity>> {
        return dataSource.getFavoriteAgents()
    }
}