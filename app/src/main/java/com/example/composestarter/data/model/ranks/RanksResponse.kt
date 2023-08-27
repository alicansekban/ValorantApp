package com.example.composestarter.data.model.ranks

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class RanksResponse(

	@Json(name="tiers")
	val tiers: List<TiersItem?>? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="assetObjectName")
	val assetObjectName: String? = null,

	@Json(name="uuid")
	val uuid: String? = null
) : Parcelable

@Parcelize
data class TiersItem(

	@Json(name="division")
	val division: String? = null,

	@Json(name="backgroundColor")
	val backgroundColor: String? = null,

	@Json(name="tier")
	val tier: Int? = null,

	@Json(name="color")
	val color: String? = null,

	@Json(name="tierName")
	val tierName: String? = null,

	@Json(name="rankTriangleUpIcon")
	val rankTriangleUpIcon: String? = null,

	@Json(name="rankTriangleDownIcon")
	val rankTriangleDownIcon: String? = null,

	@Json(name="divisionName")
	val divisionName: String? = null,

	@Json(name="largeIcon")
	val largeIcon: String? = null,

	@Json(name="smallIcon")
	val smallIcon: String? = null
) : Parcelable
