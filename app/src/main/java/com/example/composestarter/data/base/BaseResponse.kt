package com.example.composestarter.data.base

import com.squareup.moshi.Json

data class BaseResponse<T>(
    @Json(name = "status")
    val status: Int,
    @Json(name = "data")
    val data: List<T> = arrayListOf()
)