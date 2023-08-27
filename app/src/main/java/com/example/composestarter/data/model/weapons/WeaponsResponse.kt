package com.example.composestarter.data.model.weapons

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeaponsResponse(

	@Json(name="skins")
	val skins: List<SkinsItem?>? = null,

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="killStreamIcon")
	val killStreamIcon: String? = null,

	@Json(name="shopData")
	val shopData: ShopData? = null,

	@Json(name="defaultSkinUuid")
	val defaultSkinUuid: String? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="weaponStats")
	val weaponStats: WeaponStats? = null,

	@Json(name="category")
	val category: String? = null,

	@Json(name="uuid")
	val uuid: String? = null
) : Parcelable

@Parcelize
data class SkinsItem(

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="contentTierUuid")
	val contentTierUuid: String? = null,

	@Json(name="wallpaper")
	val wallpaper: String? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="chromas")
	val chromas: List<ChromasItem?>? = null,

	@Json(name="uuid")
	val uuid: String? = null,

	@Json(name="themeUuid")
	val themeUuid: String? = null,

	@Json(name="levels")
	val levels: List<LevelsItem?>? = null
) : Parcelable

@Parcelize
data class DamageRangesItem(

	@Json(name="rangeEndMeters")
	val rangeEndMeters: Double? = null,

	@Json(name="headDamage")
	val headDamage: Double? = null,

	@Json(name="bodyDamage")
	val bodyDamage: Double? = null,

	@Json(name="legDamage")
	val legDamage: Double? = null,

	@Json(name="rangeStartMeters")
	val rangeStartMeters: Double? = null
) : Parcelable

@Parcelize
data class ChromasItem(

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="swatch")
	val swatch: String? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="fullRender")
	val fullRender: String? = null,

	@Json(name="uuid")
	val uuid: String? = null,

	@Json(name="streamedVideo")
	val streamedVideo: String? = null
) : Parcelable

@Parcelize
data class LevelsItem(

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="levelItem")
	val levelItem: String? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="uuid")
	val uuid: String? = null,

	@Json(name="streamedVideo")
	val streamedVideo: String? = null
) : Parcelable

@Parcelize
data class GridPosition(

	@Json(name="column")
	val column: Int? = null,

	@Json(name="row")
	val row: Int? = null
) : Parcelable

@Parcelize
data class ShopData(

	@Json(name="canBeTrashed")
	val canBeTrashed: Boolean? = null,

	@Json(name="image")
	val image: String? = null,

	@Json(name="cost")
	val cost: Int? = null,

	@Json(name="newImage")
	val newImage: String? = null,

	@Json(name="newImage2")
	val newImage2: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="gridPosition")
	val gridPosition: GridPosition? = null,

	@Json(name="category")
	val category: String? = null,

	@Json(name="categoryText")
	val categoryText: String? = null
) : Parcelable

@Parcelize
data class AdsStats(

	@Json(name="fireRate")
	val fireRate: Double? = null,

	@Json(name="burstCount")
	val burstCount: Int? = null,

	@Json(name="runSpeedMultiplier")
	val runSpeedMultiplier: Double? = null,

	@Json(name="zoomMultiplier")
	val zoomMultiplier: Double? = null,

	@Json(name="firstBulletAccuracy")
	val firstBulletAccuracy: Double? = null
) : Parcelable

@Parcelize
data class WeaponStats(

	@Json(name="damageRanges")
	val damageRanges: List<DamageRangesItem?>? = null,

	@Json(name="equipTimeSeconds")
	val equipTimeSeconds: Double? = null,

	@Json(name="shotgunPelletCount")
	val shotgunPelletCount: Int? = null,

	@Json(name="adsStats")
	val adsStats: AdsStats? = null,

	@Json(name="fireRate")
	val fireRate: Double? = null,

	@Json(name="runSpeedMultiplier")
	val runSpeedMultiplier: Double? = null,

	@Json(name="feature")
	val feature: String? = null,

	@Json(name="airBurstStats")
	val airBurstStats: AirBurstStats? = null,

	@Json(name="reloadTimeSeconds")
	val reloadTimeSeconds: Double? = null,

	@Json(name="wallPenetration")
	val wallPenetration: String? = null,

	@Json(name="magazineSize")
	val magazineSize: Int? = null,

	@Json(name="fireMode")
	val fireMode: String? = null,

	@Json(name="firstBulletAccuracy")
	val firstBulletAccuracy: Double? = null,

	@Json(name="altFireType")
	val altFireType: String? = null,

	@Json(name="altShotgunStats")
	val altShotgunStats: AltShotgunStats? = null
) : Parcelable

@Parcelize
data class AirBurstStats(
	@Json(name="shotgunPelletCount")
	val altFireType: Int? = null,

	@Json(name="burstDistance")
	val burstDistance: Double? = null
) : Parcelable

@Parcelize
data class AltShotgunStats(
	@Json(name="shotgunPelletCount")
	val altFireType: Int? = null,

	@Json(name="burstRate")
	val burstRate: Double? = null
) : Parcelable
