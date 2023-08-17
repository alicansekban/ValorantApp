package com.example.composestarter.domain.usecase

import com.example.composestarter.data.repository.AgentsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.AgentsMapper
import com.example.composestarter.domain.model.AgentsUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AgentDetailUseCase @Inject constructor(private val repository: AgentsRepository,private val agentsMapper: AgentsMapper) {
    operator fun invoke(id:String): Flow<BaseUIModel<AgentsUIModel>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = repository.getAgentDetail(id)) {
                    is ResultWrapper.GenericError -> Error("sorun yaşandı")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("hata")
                    is ResultWrapper.Success -> Success(agentsMapper.mapAgentResponseToUIModel(response.value.data))
                }
            )
        }
    }
}