package com.example.composestarter.domain.usecase.ranks

import com.example.composestarter.data.repository.ranks.RanksRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.ranks.RanksMapper
import com.example.composestarter.domain.model.ranks.RanksUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RanksUseCase @Inject constructor(
    private val repository: RanksRepository,
    private val mapper: RanksMapper
) {
    operator fun invoke(): Flow<BaseUIModel<List<RanksUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = repository.getCompetitiveRanks()) {
                    is ResultWrapper.GenericError -> Error( "Error Occurred")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("Network Error")
                    is ResultWrapper.Success -> Success(response.value.data.map { ranksResponse ->
                        mapper.mapToUIModel(ranksResponse)
                    })
                }
            )
        }
    }
}