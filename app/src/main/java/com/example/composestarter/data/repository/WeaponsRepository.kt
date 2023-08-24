package com.example.composestarter.data.repository

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.weapons.WeaponsResponse
import com.example.composestarter.data.source.WeaponsRemoteDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class WeaponsRepository @Inject constructor(
    private val dataSource: WeaponsRemoteDataSource
) {
    suspend fun getWeapons() : ResultWrapper<BaseListResponse<WeaponsResponse>> = dataSource.getWeapons()

}