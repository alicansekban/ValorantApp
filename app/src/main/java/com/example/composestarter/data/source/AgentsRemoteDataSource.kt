package com.example.composestarter.data.source

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.agent.AgentResponse
import com.example.composestarter.data.remote.ValorantService
import com.example.composestarter.utils.ResultWrapper
import com.example.composestarter.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class AgentsRemoteDataSource @Inject constructor(private val api: ValorantService) {

    suspend fun getAgents(): ResultWrapper<BaseListResponse<AgentResponse>> =
        safeApiCall(Dispatchers.IO) { api.getAgents()}

    suspend fun getAgentDetail(id:String) : ResultWrapper<BaseResponse<AgentResponse>> = safeApiCall(Dispatchers.IO) {api.getAgentDetail(id)}
}