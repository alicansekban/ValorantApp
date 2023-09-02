package com.example.composestarter.data.local.model.skins

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "favorite_skins")
data class FavoriteSkinsEntity(
    @PrimaryKey
    val id : String,
    val video : String,
    val displayIcon : String,
    val displaySkinName : String,
    val weaponName : String
)
