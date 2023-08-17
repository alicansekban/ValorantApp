package com.example.composestarter.domain.model

data class AgentsUIModel(
    val killfeedPortrait: String? = null,
    val role: RoleUIModel? = null,
    val isFullPortraitRightFacing: Boolean? = null,
    val displayName: String? = null,
    val isBaseContent: Boolean? = null,
    val description: String? = null,
    val backgroundGradientColors: List<String?>? = null,
    val isAvailableForTest: Boolean? = null,
    val uuid: String? = null,
    val characterTags: List<String?>? = null,
    val displayIconSmall: String? = null,
    val fullPortrait: String? = null,
    val fullPortraitV2: String? = null,
    val abilities: List<AbilitiesUIModel?>? = null,
    val displayIcon: String? = null,
    val bustPortrait: String? = null,
    val background: String? = null,
    val assetPath: String? = null,
    val voiceLine: VoiceLineUIModel? = null,
    val isPlayableCharacter: Boolean? = null,
    val developerName: String? = null
)

data class VoiceLineUIModel(
    val minDuration: Double? = null,
    val mediaList: List<MediaListItemUIModel?>? = null,
    val maxDuration: Double? = null
)

data class RoleUIModel(
    val displayIcon: String? = null,
    val displayName: String? = null,
    val assetPath: String? = null,
    val description: String? = null,
    val uuid: String? = null
)

data class AbilitiesUIModel(
    val displayIcon: String? = null,
    val displayName: String? = null,
    val description: String? = null,
    val slot: String? = null
)

data class MediaListItemUIModel(
    val id: Int? = null,
    val wave: String? = null,
    val wwise: String? = null
)
