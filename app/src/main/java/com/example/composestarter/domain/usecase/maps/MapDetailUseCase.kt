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

class MapDetailUseCase @Inject constructor(private val repository: MapsRepository, private val mapper: MapsMapper) {
    operator fun invoke(id:String): Flow<BaseUIModel<MapsUIModel>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = repository.getMapDetail(id)) {
                    is ResultWrapper.GenericError -> Error("sorun yaşandı")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("hata")
                    is ResultWrapper.Success -> Success(mapper.mapResponseToUIModel(response.value.data))
                }
            )
        }
    }
}