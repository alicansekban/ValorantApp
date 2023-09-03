package com.example.composestarter.domain.usecase.favorites.agents

import com.example.composestarter.data.repository.favorites.agents.FavoriteAgentsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveFavoriteAgentUseCase @Inject constructor(
    private val repository: FavoriteAgentsRepository
) {

    operator fun invoke(id: String): Flow<BaseUIModel<Any>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.removeAgentFromFavorites(id)) {
                    is ResultWrapper.GenericError -> Error(result.error ?: "Error Occurred")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("")
                    is ResultWrapper.Success -> Success(result.value)
                }
            )
        }
    }
}