package com.example.composestarter.data.source

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.map.MapsResponse
import com.example.composestarter.data.remote.ValorantService
import com.example.composestarter.utils.ResultWrapper
import com.example.composestarter.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MapsRemoteDataSource @Inject constructor(
    private val api: ValorantService
) {
    suspend fun getMaps(): ResultWrapper<BaseListResponse<MapsResponse>> =
        safeApiCall(Dispatchers.IO) { api.getMaps() }

    suspend fun getMapDetail(id:String): ResultWrapper<BaseResponse<MapsResponse>> =
        safeApiCall(Dispatchers.IO) { api.getMapDetail(id) }
}