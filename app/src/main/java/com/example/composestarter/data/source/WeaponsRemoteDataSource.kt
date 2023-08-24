package com.example.composestarter.data.source

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.weapons.WeaponsResponse
import com.example.composestarter.data.remote.ValorantService
import com.example.composestarter.utils.ResultWrapper
import com.example.composestarter.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WeaponsRemoteDataSource @Inject constructor(
    val api: ValorantService
) {

    suspend fun getWeapons(): ResultWrapper<BaseListResponse<WeaponsResponse>> =
        safeApiCall(Dispatchers.IO) { api.getWeapons() }
}