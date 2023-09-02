package com.example.composestarter.data.repository.favorites.skins

import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity
import com.example.composestarter.data.source.favorites.skins.FavoriteSkinsLocalDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class FavoriteSkinsRepository @Inject constructor(
    private val dataSource: FavoriteSkinsLocalDataSource
) {

    suspend fun insertFavoriteSkin(skin: FavoriteSkinsEntity) : ResultWrapper<Any> {
        return dataSource.insertFavoriteSkin(skin)
    }
    fun getFavoriteSkins(searchQuery:String): ResultWrapper<List<FavoriteSkinsEntity>> {
        return dataSource.getFavoriteSkins(searchQuery)
    }
}