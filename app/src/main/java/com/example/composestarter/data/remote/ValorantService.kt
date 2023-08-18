package com.example.composestarter.data.remote

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.agent.AgentResponse
import com.example.composestarter.data.model.map.MapsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ValorantService {

    @GET("agents")
    suspend fun getAgents() : BaseListResponse<AgentResponse>

    @GET("agents/{agentUuid}")
    suspend fun getAgentDetail(@Path("agentUuid") id: String) : BaseResponse<AgentResponse>

    @GET("maps")
    suspend fun getMaps() : BaseListResponse<MapsResponse>

    @GET("maps/{mapUuid}")
    suspend fun getMapDetail(@Path("mapUuid") id: String) : BaseResponse<MapsResponse>
}
