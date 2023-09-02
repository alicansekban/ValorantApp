package com.example.composestarter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composestarter.data.local.model.agents.FavoriteAgentsEntity
import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity

@Database(entities = [FavoriteSkinsEntity::class,FavoriteAgentsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteSkinsDao(): FavoriteSkinsDao
    abstract fun favoriteAgentsDao(): FavoriteAgentsDao
}
