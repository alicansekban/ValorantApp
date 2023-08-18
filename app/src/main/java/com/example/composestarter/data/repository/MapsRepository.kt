package com.example.composestarter.data.repository

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.base.BaseResponse
import com.example.composestarter.data.model.map.MapsResponse
import com.example.composestarter.data.source.MapsRemoteDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class MapsRepository @Inject constructor(
    private val dataSource: MapsRemoteDataSource
) {
    suspend fun getMaps() : ResultWrapper<BaseListResponse<MapsResponse>> = dataSource.getMaps()
    suspend fun getMapDetail(id:String) : ResultWrapper<BaseResponse<MapsResponse>> = dataSource.getMapDetail(id)
}