package com.example.composestarter.data.source

import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.AgentResponse
import com.example.composestarter.data.remote.ValorantService
import com.example.composestarter.utils.ResultWrapper
import com.example.composestarter.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class AgentsRemoteDataSource @Inject constructor(private val api: ValorantService) {

    suspend fun getAgents(): ResultWrapper<BaseResponse<AgentResponse>> =
        safeApiCall(Dispatchers.IO) { api.getAgents()}
}