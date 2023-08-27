package com.example.composestarter.domain.mapper.maps

import com.example.composestarter.data.model.map.CalloutsItem
import com.example.composestarter.data.model.map.Location
import com.example.composestarter.data.model.map.MapsResponse
import com.example.composestarter.domain.model.maps.CalloutsItemUIModel
import com.example.composestarter.domain.model.maps.LocationUIModel
import com.example.composestarter.domain.model.maps.MapsUIModel
import javax.inject.Inject

class MapsMapper @Inject constructor() {

    fun mapResponseToUIModel(response: MapsResponse): MapsUIModel {
        val calloutsUI = response.callouts?.map { mapCalloutsItemToUIModel(it) }
        return MapsUIModel(
            callouts = calloutsUI,
            displayName = response.displayName,
            listViewIcon = response.listViewIcon,
            coordinates = response.coordinates,
            yScalarToAdd = response.yScalarToAdd,
            yMultiplier = response.yMultiplier,
            uuid = response.uuid,
            displayIcon = response.displayIcon,
            xMultiplier = response.xMultiplier,
            xScalarToAdd = response.xScalarToAdd,
            assetPath = response.assetPath,
            mapUrl = response.mapUrl,
            splash = response.splash
        )
    }

    private fun mapCalloutsItemToUIModel(calloutsItem: CalloutsItem?): CalloutsItemUIModel? {
        return calloutsItem?.let {
            CalloutsItemUIModel(
                superRegionName = it.superRegionName,
                regionName = it.regionName,
                location = mapLocationToUIModel(it.location)
            )
        }
    }

    private fun mapLocationToUIModel(location: Location?): LocationUIModel? {
        return location?.let {
            LocationUIModel(
                x = it.x,
                y = it.y
            )
        }
    }
}