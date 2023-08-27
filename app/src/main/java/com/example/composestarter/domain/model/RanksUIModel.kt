package com.example.composestarter.domain.model

data class RanksUIModel(
    val tiers: List<UIModelTiersItem>? = null,
    val assetPath: String? = null,
    val assetObjectName: String? = null,
    val uuid: String? = null
)

data class UIModelTiersItem(
    val division: String? = null,
    val backgroundColor: String? = null,
    val tier: Int? = null,
    val color: String? = null,
    val tierName: String? = null,
    val rankTriangleUpIcon: String? = null,
    val rankTriangleDownIcon: String? = null,
    val divisionName: String? = null,
    val largeIcon: String? = null,
    val smallIcon: String? = null
)
