package com.example.composestarter.domain.usecase.bundles

import com.example.composestarter.data.repository.BundlesRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.BundlesMapper
import com.example.composestarter.domain.model.BundlesUIModel
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
                    is ResultWrapper.GenericError -> Error("sorun yaşandı")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("hata")
                    is ResultWrapper.Success -> Success(response.value.data.map { bundlesResponse ->
                        mapper.mapToUIModel(bundlesResponse)
                    })
                }
            )
        }
    }
}