package com.example.composestarter.data.source.favorites.agents

import com.example.composestarter.data.local.AppDatabase
import com.example.composestarter.data.local.model.agents.FavoriteAgentsEntity
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class FavoriteAgentsLocalDataSource @Inject constructor(
    private val db: AppDatabase
) {

    private suspend fun isFavoriteAgentExist(id: String): Boolean {
        val count = db.favoriteAgentsDao().isFavoriteAdded(id)
        return count > 0
    }

    suspend fun insertFavoriteAgent(agent: FavoriteAgentsEntity): ResultWrapper<Any> {
        return try {
            if (isFavoriteAgentExist(agent.id)) {
                ResultWrapper.GenericError(error = "This skin has been added before.")
            } else {
                ResultWrapper.Loading
                ResultWrapper.Success(db.favoriteAgentsDao().insertFavoriteAgent(agent))
            }
        } catch (e: Exception) {
            ResultWrapper.GenericError(error = e.message)
        }
    }

    suspend fun removeAgentFromFavorites(id: String): ResultWrapper<Any> {
        return try {
            ResultWrapper.Loading
            ResultWrapper.Success(db.favoriteAgentsDao().removeAgentFromFavorites(id))

        } catch (e: Exception) {
            ResultWrapper.GenericError(error = e.message)
        }
    }

    fun getFavoriteAgents(): ResultWrapper<List<FavoriteAgentsEntity>> {
        return try {
            ResultWrapper.Loading
            ResultWrapper.Success(db.favoriteAgentsDao().getFavoriteAgents())
        } catch (e: Exception) {
            ResultWrapper.GenericError(error = e.message)
        }
    }
}