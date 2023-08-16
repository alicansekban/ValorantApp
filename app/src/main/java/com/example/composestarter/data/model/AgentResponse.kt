package com.example.composestarter.data.model

import com.squareup.moshi.Json


data class AgentResponse(

	@Json(name="killfeedPortrait")
	val killfeedPortrait: String? = null,

	@Json(name="role")
	val role: Role? = null,

	@Json(name="isFullPortraitRightFacing")
	val isFullPortraitRightFacing: Boolean? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="isBaseContent")
	val isBaseContent: Boolean? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="backgroundGradientColors")
	val backgroundGradientColors: List<String?>? = null,

	@Json(name="isAvailableForTest")
	val isAvailableForTest: Boolean? = null,

	@Json(name="uuid")
	val uuid: String? = null,

	@Json(name="characterTags")
	val characterTags: Any? = null,

	@Json(name="displayIconSmall")
	val displayIconSmall: String? = null,

	@Json(name="fullPortrait")
	val fullPortrait: String? = null,

	@Json(name="fullPortraitV2")
	val fullPortraitV2: String? = null,

	@Json(name="abilities")
	val abilities: List<AbilitiesItem?>? = null,

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="bustPortrait")
	val bustPortrait: String? = null,

	@Json(name="background")
	val background: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="voiceLine")
	val voiceLine: VoiceLine? = null,

	@Json(name="isPlayableCharacter")
	val isPlayableCharacter: Boolean? = null,

	@Json(name="developerName")
	val developerName: String? = null
)

data class VoiceLine(

	@Json(name="minDuration")
	val minDuration: Any? = null,

	@Json(name="mediaList")
	val mediaList: List<MediaListItem?>? = null,

	@Json(name="maxDuration")
	val maxDuration: Any? = null
)

data class Role(

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="assetPath")
	val assetPath: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="uuid")
	val uuid: String? = null
)

data class AbilitiesItem(

	@Json(name="displayIcon")
	val displayIcon: String? = null,

	@Json(name="displayName")
	val displayName: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="slot")
	val slot: String? = null
)

data class MediaListItem(

	@Json(name="id")
	val id: Int? = null,

	@Json(name="wave")
	val wave: String? = null,

	@Json(name="wwise")
	val wwise: String? = null
)
