package com.example.composestarter.data.repository.seasons

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.seasons.SeasonsResponse
import com.example.composestarter.data.source.seasons.SeasonsRemoteDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class SeasonsRepository @Inject constructor(private val remoteDataSource: SeasonsRemoteDataSource) {

    suspend fun getCompetitiveSeasons() : ResultWrapper<BaseListResponse<SeasonsResponse>> = remoteDataSource.getCompetitiveSeasons()
}