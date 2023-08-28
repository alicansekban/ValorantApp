package com.example.composestarter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composestarter.data.local.model.DataModel

@Database(entities = [DataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteSkinsDao(): FavoriteSkinsDao
    abstract fun favoriteAgentsDao(): FavoriteAgentsDao
}
