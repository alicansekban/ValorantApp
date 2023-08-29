package com.example.composestarter.data.model.map

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapsResponse(

	@Json(name="callouts")
	val callouts: List<CalloutsItem?>? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="narrativeDescription")
	val narrativeDescription: String? = null,

	@Json(name="listViewIcon")
	val listViewIcon: String? = null,

	@Json(name="coordinates")
	val coordinates: String? = null,

	@Json(name="yScalarToAdd")
	val yScalarToAdd: Double? = null,

	@Json(name="yMultiplier")
	val yMultiplier: Double? = null,

	@Json(name="uuid")
	val uuid: String? = null,

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="xMultiplier")
	val xMultiplier: Double? = null,

	@Json(name="xScalarToAdd")
	val xScalarToAdd: Double? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="mapUrl")
	val mapUrl: String? = null,

	@Json(name="splash")
	val splash: String? = null
) : Parcelable

@Parcelize
data class Location(

	@Json(name="x")
	val x: Double? = null,

	@Json(name="y")
	val y: Double? = null
) : Parcelable

@Parcelize
data class 	CalloutsItem(

	@Json(name="superRegionName")
	val superRegionName: String? = null,

	@Json(name="regionName")
	val regionName: String? = null,

	@Json(name="location")
	val location: Location? = null
) : Parcelable
