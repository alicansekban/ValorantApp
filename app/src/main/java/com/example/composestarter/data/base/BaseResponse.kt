package com.example.composestarter.data.base

import com.squareup.moshi.Json

data class BaseResponse<T>(
    @Json(name = "success")
    val success: Int,
    @Json(name = "data")
    val data: T,
)