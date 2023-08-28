package com.example.composestarter.data.local.model.agents

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AgentsEntity(
    @PrimaryKey
    val id : Int
)
