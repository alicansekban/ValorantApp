package com.example.composestarter.domain.usecase.bundles

import com.example.composestarter.data.repository.bundles.BundlesRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.bundles.BundlesMapper
import com.example.composestarter.domain.model.bundles.BundlesUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BundlesUseCase @Inject constructor(
    private val repository: BundlesRepository,
    private val mapper: BundlesMapper
) {
    operator fun invoke(): Flow<BaseUIModel<List<BundlesUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when (val response = repository.getBundles()) {
                    is ResultWrapper.GenericError -> Error( "Error Occurred")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("Network Error")
                    is ResultWrapper.Success -> Success(response.value.data.map { bundlesResponse ->
                        mapper.mapToUIModel(bundlesResponse)
                    })
                }
            )
        }
    }
}