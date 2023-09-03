package com.example.composestarter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.composestarter.data.local.model.agents.FavoriteAgentsEntity

@Dao
interface FavoriteAgentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteAgent(dataModel: FavoriteAgentsEntity)

    @Query("delete from favorite_agents Where id = :id")
    suspend fun removeAgentFromFavorites(id:String)

    @Query("select  COUNT(*)  from favorite_agents Where id = :id")
    suspend fun isFavoriteAdded(id:String) : Int

    @Query("select * from favorite_agents")
    fun getFavoriteAgents() : List<FavoriteAgentsEntity>
}
