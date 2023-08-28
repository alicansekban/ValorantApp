package com.example.composestarter.domain.usecase.seasons

import com.example.composestarter.data.repository.seasons.SeasonsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.seasons.SeasonsMapper
import com.example.composestarter.domain.model.seasons.SeasonsUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SeasonsUseCase @Inject constructor(
    private val repository: SeasonsRepository,
    private val mapper: SeasonsMapper
) {

    operator fun invoke(): Flow<BaseUIModel<List<SeasonsUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = repository.getCompetitiveSeasons()) {
                    is ResultWrapper.GenericError -> Error("sorun yaşandı")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("hata")
                    is ResultWrapper.Success -> Success(response.value.data.map { seasonsResponse ->
                        mapper.mapResponseToUIModel(seasonsResponse)
                    })
                }
            )
        }
    }
}