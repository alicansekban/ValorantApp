package com.example.composestarter.domain.usecase.favorites.skins

import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity
import com.example.composestarter.data.repository.favorites.skins.FavoriteSkinsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteSkinsUseCase @Inject constructor(
    private val repository: FavoriteSkinsRepository
) {

    suspend  operator fun invoke(searchQuery: String = ""): Flow<BaseUIModel<List<FavoriteSkinsEntity>>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getFavoriteSkins(searchQuery)) {
                    is ResultWrapper.GenericError -> Error(result.error ?: "Error Occurred")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("")
                    is ResultWrapper.Success -> Success(result.value)
                }
            )
        }
    }
}