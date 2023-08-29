package com.example.composestarter.domain.model.maps

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MapsUIModel(
    val callouts: List<CalloutsItemUIModel?>? = null,
    val displayName: String? = null,
    val listViewIcon: String? = null,
    val coordinates: String? = null,
    val narrativeDescription: String? = null,
    val yScalarToAdd: Double? = null,
    val yMultiplier: Double? = null,
    val uuid: String? = null,
    val displayIcon: String? = null,
    val xMultiplier: Double? = null,
    val xScalarToAdd: Double? = null,
    val assetPath: String? = null,
    val mapUrl: String? = null,
    val splash: String? = null
) : Parcelable

@Parcelize
data class CalloutsItemUIModel(
    val superRegionName: String? = null,
    val regionName: String? = null,
    val location: LocationUIModel? = null
) : Parcelable

@Parcelize
data class LocationUIModel(
    val x: Double? = null,
    val y: Double? = null
) : Parcelable

