package com.example.composestarter.domain.mapper.seasons

import com.example.composestarter.data.model.seasons.SeasonsResponse
import com.example.composestarter.domain.model.seasons.SeasonsUIModel
import javax.inject.Inject

class SeasonsMapper @Inject constructor() {
    fun mapResponseToUIModel(response: SeasonsResponse): SeasonsUIModel {
        return SeasonsUIModel(
            displayName = response.displayName,
            assetPath = response.assetPath,
            startTime = response.startTime,
            endTime = response.endTime,
            type = response.type,
            uuid = response.uuid,
            parentUuid = response.parentUuid
        )
    }
}