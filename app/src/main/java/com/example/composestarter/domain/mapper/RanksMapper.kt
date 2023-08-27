package com.example.composestarter.domain.mapper

import com.example.composestarter.data.model.ranks.RanksResponse
import com.example.composestarter.domain.model.RanksUIModel
import com.example.composestarter.domain.model.UIModelTiersItem
import javax.inject.Inject

class RanksMapper @Inject constructor(){
    fun mapToUIModel(ranksResponse: RanksResponse): RanksUIModel {
        val tiers = ranksResponse.tiers?.map { tierItem ->
            UIModelTiersItem(
                division = tierItem?.division,
                backgroundColor = tierItem?.backgroundColor,
                tier = tierItem?.tier,
                color = tierItem?.color,
                tierName = tierItem?.tierName,
                rankTriangleUpIcon = tierItem?.rankTriangleUpIcon,
                rankTriangleDownIcon = tierItem?.rankTriangleDownIcon,
                divisionName = tierItem?.divisionName,
                largeIcon = tierItem?.largeIcon,
                smallIcon = tierItem?.smallIcon
            )
        }
        return RanksUIModel(
            tiers = tiers,
            assetPath = ranksResponse.assetPath,
            assetObjectName = ranksResponse.assetObjectName,
            uuid = ranksResponse.uuid
        )
    }
}