package com.example.composestarter.domain.usecase.ranks

import com.example.composestarter.data.repository.RanksRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.RanksMapper
import com.example.composestarter.domain.model.RanksUIModel
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
                    is ResultWrapper.GenericError -> Error("sorun yaşandı")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("hata")
                    is ResultWrapper.Success -> Success(response.value.data.map { ranksResponse ->
                        mapper.mapToUIModel(ranksResponse)
                    })
                }
            )
        }
    }
}