package com.example.composestarter.data.source.favorites.skins

import com.example.composestarter.data.local.AppDatabase
import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class FavoriteSkinsLocalDataSource @Inject constructor(
    private val db: AppDatabase
) {

    private suspend fun isFavoriteSkinExist(id: String): Boolean {
        val count = db.favoriteSkinsDao().isFavoriteAdded(id)
        return count > 0
    }

    suspend fun insertFavoriteSkin(skin: FavoriteSkinsEntity): ResultWrapper<Any> {
        return try {
            if (isFavoriteSkinExist(skin.id)) {
                ResultWrapper.GenericError(error = "This skin has been added before.")
            } else {
                ResultWrapper.Loading
                ResultWrapper.Success(db.favoriteSkinsDao().insertFavoriteSkin(skin))
            }
        } catch (e: Exception) {
            ResultWrapper.GenericError(error = e.message)
        }
    }

    fun getFavoriteSkins(searchQuery: String): ResultWrapper<List<FavoriteSkinsEntity>> {
        return try {
            ResultWrapper.Loading
            ResultWrapper.Success(db.favoriteSkinsDao().getFavoriteMovies(searchQuery))

        } catch (e: Exception) {
            ResultWrapper.GenericError(error = e.message)
        }
    }
}