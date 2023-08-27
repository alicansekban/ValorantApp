package com.example.composestarter.data.source.bundles

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.bundles.BundleResponse
import com.example.composestarter.data.remote.ValorantService
import com.example.composestarter.utils.ResultWrapper
import com.example.composestarter.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BundlesRemoteDataSource @Inject constructor(val api : ValorantService) {
    suspend fun getBundles(): ResultWrapper<BaseListResponse<BundleResponse>> =
        safeApiCall(Dispatchers.IO) { api.getBundles() }
}