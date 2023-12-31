package com.example.composestarter.data.repository.bundles

import com.example.composestarter.data.base.BaseListResponse
import com.example.composestarter.data.model.bundles.BundleResponse
import com.example.composestarter.data.source.bundles.BundlesRemoteDataSource
import com.example.composestarter.utils.ResultWrapper
import javax.inject.Inject

class BundlesRepository @Inject constructor(
    private val dataSource: BundlesRemoteDataSource
) {
    suspend fun getBundles() : ResultWrapper<BaseListResponse<BundleResponse>> = dataSource.getBundles()
}