package com.example.composestarter.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Database Model")
data class DataModel(
    @PrimaryKey
    val result: String
)
