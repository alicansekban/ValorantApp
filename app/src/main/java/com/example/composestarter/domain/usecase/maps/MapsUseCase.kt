package com.example.composestarter.domain.usecase.maps

import com.example.composestarter.data.repository.maps.MapsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.maps.MapsMapper
import com.example.composestarter.domain.model.maps.MapsUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapsUseCase @Inject constructor(private val mapsRepository: MapsRepository, private val mapper: MapsMapper) {

    operator fun invoke(): Flow<BaseUIModel<List<MapsUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = mapsRepository.getMaps()) {
                    is ResultWrapper.GenericError -> Error( "Error Occurred")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("Network Error")
                    is ResultWrapper.Success -> Success(response.value.data.map { agentResponse ->
                        mapper.mapResponseToUIModel(agentResponse)
                    })
                }
            )
        }
    }
}