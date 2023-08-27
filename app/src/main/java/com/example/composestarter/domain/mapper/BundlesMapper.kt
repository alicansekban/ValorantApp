package com.example.composestarter.domain.mapper

import com.example.composestarter.data.model.bundles.BundleResponse
import com.example.composestarter.domain.model.BundlesUIModel
import javax.inject.Inject

class BundlesMapper @Inject constructor() {

    fun mapToUIModel(bundleResponse: BundleResponse): BundlesUIModel {
        return BundlesUIModel(
            displayNameSubText = bundleResponse.displayNameSubText,
            extraDescription = bundleResponse.extraDescription,
            displayIcon = bundleResponse.displayIcon,
            displayName = bundleResponse.displayName,
            assetPath = bundleResponse.assetPath,
            useAdditionalContext = bundleResponse.useAdditionalContext,
            verticalPromoImage = bundleResponse.verticalPromoImage,
            description = bundleResponse.description,
            uuid = bundleResponse.uuid,
            promoDescription = bundleResponse.promoDescription,
            displayIcon2 = bundleResponse.displayIcon2
        )
    }
}