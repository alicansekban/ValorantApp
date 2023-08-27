package com.example.composestarter.data.repository.agents

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.agent.AgentResponse
import com.example.composestarter.data.source.agents.AgentsRemoteDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class AgentsRepository @Inject constructor(private val dataSource: AgentsRemoteDataSource) {
    suspend fun getAgents(): ResultWrapper<BaseListResponse<AgentResponse>> = dataSource.getAgents()
    suspend fun getAgentDetail(id:String): ResultWrapper<BaseResponse<AgentResponse>> = dataSource.getAgentDetail(id)

}