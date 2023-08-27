package com.example.composestarter.domain.usecase.agents

import com.example.composestarter.data.repository.agents.AgentsRepository
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.mapper.agents.AgentsMapper
import com.example.composestarter.domain.model.agents.AgentsUIModel
import com.example.composestarter.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AgentsUseCase @Inject constructor(
    private val repository: AgentsRepository,
    private val agentsMapper: AgentsMapper
) {
    operator fun invoke(): Flow<BaseUIModel<List<AgentsUIModel>>> {
        return flow {
            emit(Loading())
            emit(
                when(val response = repository.getAgents()) {
                    is ResultWrapper.GenericError -> Error("sorun yaşandı")
                    ResultWrapper.Loading ->Loading()
                    ResultWrapper.NetworkError ->Error("hata")
                    is ResultWrapper.Success -> Success(response.value.data.map { agentResponse ->
                        agentsMapper.mapAgentResponseToUIModel(agentResponse)
                    })
                }
            )
        }
    }
}