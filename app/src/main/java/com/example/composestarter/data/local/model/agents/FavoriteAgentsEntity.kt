package com.example.composestarter.data.local.model.agents

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_agents")
data class FavoriteAgentsEntity(
    @PrimaryKey
    val id: String,
    val displayName: String,
    val role: String,
    val roleDisplayIcon: String,
    val fullPortrait: String,
    val displayIcon: String,
    val description: String,
    val voiceLine: String,
)
