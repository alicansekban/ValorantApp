package com.example.composestarter.data.model.seasons

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonsResponse(

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="startTime")
	val startTime: String? = null,

	@Json(name="endTime")
	val endTime: String? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="uuid")
	val uuid: String? = null,

	@Json(name="parentUuid")
	val parentUuid: String? = null
) : Parcelable
