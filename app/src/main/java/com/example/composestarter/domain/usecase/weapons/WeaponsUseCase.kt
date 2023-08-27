package com.example.composestarter.domain.usecase.weapons

import com.example.composestarter.data.repository.WeaponsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.WeaponsMapper
import com.example.composestarter.domain.model.WeaponsUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeaponsUseCase @Inject constructor(
    private val repository: WeaponsRepository,
    private val mapper: WeaponsMapper
) {
    operator fun invoke(): Flow<BaseUIModel<List<WeaponsUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = repository.getWeapons()) {
                    is ResultWrapper.GenericError -> Error("sorun yaşandı")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("hata")
                    is ResultWrapper.Success -> Success(response.value.data.map { agentResponse ->
                        mapper.mapResponseToUI(agentResponse)
                    })
                }
            )
        }
    }
}