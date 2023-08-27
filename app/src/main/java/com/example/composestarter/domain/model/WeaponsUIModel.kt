package com.example.composestarter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponsUIModel(
    val skins: List<SkinsItemUIModel>?,
    val displayIcon: String?,
    val killStreamIcon: String?,
    val shopData: ShopDataUIModel?,
    val defaultSkinUuid: String?,
    val displayName: String?,
    val assetPath: String?,
    val weaponStats: WeaponStatsUIModel?,
    val category: String?,
    val uuid: String?
) : Parcelable

@Parcelize
data class SkinsItemUIModel(
    val displayIcon: String?,
    val contentTierUuid: String?,
    val wallpaper: String?,
    val displayName: String?,
    val assetPath: String?,
    val chromas: List<ChromasItemUIModel>?,
    val uuid: String?,
    val themeUuid: String?,
    val levels: List<LevelsItemUIModel>?
) : Parcelable

@Parcelize
data class ChromasItemUIModel(
    val displayIcon: String?,
    val swatch: String?,
    val displayName: String?,
    val assetPath: String?,
    val fullRender: String?,
    val uuid: String?,
    val streamedVideo: String?
) : Parcelable

@Parcelize
data class LevelsItemUIModel(
    val displayIcon: String?,
    val levelItem: String?,
    val displayName: String?,
    val assetPath: String?,
    val uuid: String?,
    val streamedVideo: String?
) : Parcelable

@Parcelize
data class ShopDataUIModel(
    val canBeTrashed: Boolean?,
    val image: String?,
    val cost: Int?,
    val newImage: String?,
    val newImage2: String?,
    val assetPath: String?,
    val gridPosition: GridPositionUIModel?,
    val category: String?,
    val categoryText: String?
) : Parcelable

@Parcelize
data class GridPositionUIModel(
    val column: Int?,
    val row: Int?
) : Parcelable

@Parcelize
data class WeaponStatsUIModel(
    val damageRanges: List<DamageRangesItemUIModel>?,
    val equipTimeSeconds: Double?,
    val shotgunPelletCount: Int?,
    val adsStats: AdsStatsUIModel?,
    val fireRate: Double?,
    val runSpeedMultiplier: Double?,
    val feature: String?,
    val airBurstStats: AirBurstStatsUIModel?,
    val reloadTimeSeconds: Double?,
    val wallPenetration: String?,
    val magazineSize: Int?,
    val fireMode: String?,
    val firstBulletAccuracy: Double?,
    val altFireType: String?,
    val altShotgunStats: AltShotgunStatsUIModel?
) : Parcelable

@Parcelize
data class DamageRangesItemUIModel(
    val rangeEndMeters: Double?,
    val headDamage: Double?,
    val bodyDamage: Double?,
    val legDamage: Double?,
    val rangeStartMeters: Double?
) : Parcelable

@Parcelize
data class AdsStatsUIModel(
    val fireRate: Double?,
    val burstCount: Int?,
    val runSpeedMultiplier: Double?,
    val zoomMultiplier: Double?,
    val firstBulletAccuracy: Double?
) : Parcelable

@Parcelize
data class AirBurstStatsUIModel(
    val altFireType: Int?,
    val burstDistance: Double?
) : Parcelable

@Parcelize
data class AltShotgunStatsUIModel(
    val altFireType: Int?,
    val burstRate: Double?
) : Parcelable

