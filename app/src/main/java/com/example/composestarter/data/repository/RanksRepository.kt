package com.example.composestarter.data.repository

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.ranks.RanksResponse
import com.example.composestarter.data.source.RanksRemoteDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class RanksRepository @Inject constructor(
    private val dataSource: RanksRemoteDataSource
) {
    suspend fun getCompetitiveRanks() : ResultWrapper<BaseListResponse<RanksResponse>> = dataSource.getCompetitiveRanks()
}