package com.example.composestarter.data.remote.webservice

import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.AgentResponse
import retrofit2.http.GET

interface WebService {

    @GET("agents")
    suspend fun getAgents() : BaseResponse<AgentResponse>
}
