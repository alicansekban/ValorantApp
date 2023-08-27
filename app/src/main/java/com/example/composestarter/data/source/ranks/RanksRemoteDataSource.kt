package com.example.composestarter.data.source.ranks

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.ranks.RanksResponse
import com.example.composestarter.data.remote.ValorantService
import com.example.composestarter.utils.ResultWrapper
import com.example.composestarter.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RanksRemoteDataSource @Inject constructor(val api : ValorantService) {
    suspend fun getCompetitiveRanks(): ResultWrapper<BaseListResponse<RanksResponse>> =
        safeApiCall(Dispatchers.IO) { api.getCompetitiveRanks() }
}