package com.example.composestarter.domain.usecase.weapons

import com.example.composestarter.data.repository.weapons.WeaponsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.weapons.WeaponsMapper
import com.example.composestarter.domain.model.weapons.WeaponsUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeaponDetailUseCase @Inject constructor(
    private val repository: WeaponsRepository,
    private val mapper: WeaponsMapper
) {

    operator fun invoke(id:String): Flow<BaseUIModel<WeaponsUIModel>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = repository.getWeaponDetail(id)) {
                    is ResultWrapper.GenericError -> Error( "Error Occurred")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("Network Error")
                    is ResultWrapper.Success -> Success(mapper.mapResponseToUI(response.value.data))
                }
            )
        }
    }
}