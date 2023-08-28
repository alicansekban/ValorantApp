package com.example.composestarter.data.source.seasons

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.seasons.SeasonsResponse
import com.example.composestarter.data.remote.ValorantService
import com.example.composestarter.utils.ResultWrapper
import com.example.composestarter.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SeasonsRemoteDataSource @Inject constructor(val api : ValorantService){

    suspend fun getCompetitiveSeasons(): ResultWrapper<BaseListResponse<SeasonsResponse>> =
        safeApiCall(Dispatchers.IO) { api.getCompetitiveSeasons() }
}