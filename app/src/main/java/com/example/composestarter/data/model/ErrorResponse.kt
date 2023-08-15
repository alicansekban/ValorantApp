package com.example.composestarter.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ErrorResponse(

    @Json(name = "errors")
    val errors: List<ErrorsItem?>? = null,

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "code")
    val code: String? = null
) : Parcelable

@Parcelize
data class ErrorsItem(

	@Json(name = "detail")
	val detail: String? = null,

	@Json(name = "status")
	val status: String? = null
) : Parcelable
