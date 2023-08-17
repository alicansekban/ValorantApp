package com.example.composestarter.data.repository

import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.AgentResponse
import com.example.composestarter.data.source.AgentsRemoteDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class AgentsRepository @Inject constructor(private val dataSource: AgentsRemoteDataSource) {
    suspend fun getAgents(): ResultWrapper<BaseResponse<AgentResponse>> = dataSource.getAgents()

}