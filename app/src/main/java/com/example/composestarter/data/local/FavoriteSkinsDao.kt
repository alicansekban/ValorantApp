package com.example.composestarter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity

@Dao
interface FavoriteSkinsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteSkin(dataModel: FavoriteSkinsEntity)

    @Query("select  COUNT(*)  from favorite_skins Where id = :id")
    suspend fun isFavoriteAdded(id:String) : Int

    @Query("select * from favorite_skins Where displaySkinName LIKE '%' || :searchQuery || '%'")
    fun getFavoriteMovies(searchQuery : String) : List<FavoriteSkinsEntity>
}
