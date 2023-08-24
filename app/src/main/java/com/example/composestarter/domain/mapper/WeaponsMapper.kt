package com.example.composestarter.domain.mapper

import com.example.composestarter.data.model.weapons.AdsStats
import com.example.composestarter.data.model.weapons.AirBurstStats
import com.example.composestarter.data.model.weapons.AltShotgunStats
import com.example.composestarter.data.model.weapons.ChromasItem
import com.example.composestarter.data.model.weapons.DamageRangesItem
import com.example.composestarter.data.model.weapons.GridPosition
import com.example.composestarter.data.model.weapons.LevelsItem
import com.example.composestarter.data.model.weapons.SkinsItem
import com.example.composestarter.data.model.weapons.WeaponStats
import com.example.composestarter.data.model.weapons.WeaponsResponse
import com.example.composestarter.domain.model.AdsStatsUIModel
import com.example.composestarter.domain.model.AirBurstStatsUIModel
import com.example.composestarter.domain.model.AltShotgunStatsUIModel
import com.example.composestarter.domain.model.ChromasItemUIModel
import com.example.composestarter.domain.model.DamageRangesItemUIModel
import com.example.composestarter.domain.model.GridPositionUIModel
import com.example.composestarter.domain.model.LevelsItemUIModel
import com.example.composestarter.domain.model.ShopDataUIModel
import com.example.composestarter.domain.model.SkinsItemUIModel
import com.example.composestarter.domain.model.WeaponStatsUIModel
import com.example.composestarter.domain.model.WeaponsUIModel
import javax.inject.Inject
import com.example.composestarter.data.model.weapons.ShopData as ShopData1

class WeaponsMapper @Inject constructor() {

    fun mapResponseToUI(response: WeaponsResponse): WeaponsUIModel {
        return WeaponsUIModel(
            skins = mapSkinsList(response.skins),
            displayIcon = response.displayIcon,
            killStreamIcon = response.killStreamIcon,
            shopData = mapShopData(response.shopData),
            defaultSkinUuid = response.defaultSkinUuid,
            displayName = response.displayName,
            assetPath = response.assetPath,
            weaponStats = mapWeaponStats(response.weaponStats),
            category = response.category,
            uuid = response.uuid
        )
    }

    private fun mapSkinsList(skinsList: List<SkinsItem?>?): List<SkinsItemUIModel>? {
        return skinsList?.map { mapSkinsItem(it) }
    }

    private fun mapSkinsItem(skinsItem: SkinsItem?): SkinsItemUIModel {
        return SkinsItemUIModel(
            displayIcon = skinsItem?.displayIcon,
            contentTierUuid = skinsItem?.contentTierUuid,
            wallpaper = skinsItem?.wallpaper,
            displayName = skinsItem?.displayName,
            assetPath = skinsItem?.assetPath,
            chromas = skinsItem?.chromas?.map { mapChromasItem(it) },
            uuid = skinsItem?.uuid,
            themeUuid = skinsItem?.themeUuid,
            levels = skinsItem?.levels?.map { mapLevelsItem(it) }
        )
    }

    private fun mapChromasItem(chromasItem: ChromasItem?): ChromasItemUIModel {
        return ChromasItemUIModel(
            displayIcon = chromasItem?.displayIcon,
            swatch = chromasItem?.swatch,
            displayName = chromasItem?.displayName,
            assetPath = chromasItem?.assetPath,
            fullRender = chromasItem?.fullRender,
            uuid = chromasItem?.uuid,
            streamedVideo = chromasItem?.streamedVideo
        )
    }

    private fun mapLevelsItem(levelsItem: LevelsItem?): LevelsItemUIModel {
        return LevelsItemUIModel(
            displayIcon = levelsItem?.displayIcon,
            levelItem = levelsItem?.levelItem,
            displayName = levelsItem?.displayName,
            assetPath = levelsItem?.assetPath,
            uuid = levelsItem?.uuid,
            streamedVideo = levelsItem?.streamedVideo
        )
    }

    private fun mapShopData(shopData: ShopData1?): ShopDataUIModel {
        return ShopDataUIModel(
            canBeTrashed = shopData?.canBeTrashed,
            image = shopData?.image,
            cost = shopData?.cost,
            newImage = shopData?.newImage,
            newImage2 = shopData?.newImage2,
            assetPath = shopData?.assetPath,
            gridPosition = mapGridPosition(shopData?.gridPosition),
            category = shopData?.category,
            categoryText = shopData?.categoryText
        )
    }

    private fun mapGridPosition(gridPosition: GridPosition?): GridPositionUIModel {
        return GridPositionUIModel(
            column = gridPosition?.column,
            row = gridPosition?.row
        )
    }

    private fun mapWeaponStats(weaponStats: WeaponStats?): WeaponStatsUIModel {
        return WeaponStatsUIModel(
            damageRanges = weaponStats?.damageRanges?.map { mapDamageRangesItem(it) },
            equipTimeSeconds = weaponStats?.equipTimeSeconds,
            shotgunPelletCount = weaponStats?.shotgunPelletCount,
            adsStats = mapAdsStats(weaponStats?.adsStats),
            fireRate = weaponStats?.fireRate,
            runSpeedMultiplier = weaponStats?.runSpeedMultiplier,
            feature = weaponStats?.feature,
            airBurstStats = mapAirBurstStats(weaponStats?.airBurstStats),
            reloadTimeSeconds = weaponStats?.reloadTimeSeconds,
            wallPenetration = weaponStats?.wallPenetration,
            magazineSize = weaponStats?.magazineSize,
            fireMode = weaponStats?.fireMode,
            firstBulletAccuracy = weaponStats?.firstBulletAccuracy,
            altFireType = weaponStats?.altFireType,
            altShotgunStats = mapAltShotgunStats(weaponStats?.altShotgunStats)
        )
    }

    private fun mapAdsStats(adsStats: AdsStats?): AdsStatsUIModel {
        return AdsStatsUIModel(
            fireRate = adsStats?.fireRate,
            burstCount = adsStats?.burstCount,
            runSpeedMultiplier = adsStats?.runSpeedMultiplier,
            zoomMultiplier = adsStats?.zoomMultiplier,
            firstBulletAccuracy = adsStats?.firstBulletAccuracy
        )
    }

    private fun mapAirBurstStats(airBurstStats: AirBurstStats?): AirBurstStatsUIModel {
        return AirBurstStatsUIModel(
            altFireType = airBurstStats?.altFireType,
            burstDistance = airBurstStats?.burstDistance
        )
    }

    private fun mapAltShotgunStats(altShotgunStats: AltShotgunStats?): AltShotgunStatsUIModel {
        return AltShotgunStatsUIModel(
            altFireType = altShotgunStats?.altFireType,
            burstRate = altShotgunStats?.burstRate
        )
    }

    private fun mapDamageRangesItem(damageRangesItem: DamageRangesItem?): DamageRangesItemUIModel {
        return DamageRangesItemUIModel(
            rangeEndMeters = damageRangesItem?.rangeEndMeters,
            headDamage = damageRangesItem?.headDamage,
            bodyDamage = damageRangesItem?.bodyDamage,
            legDamage = damageRangesItem?.legDamage,
            rangeStartMeters = damageRangesItem?.rangeStartMeters
        )
    }
}