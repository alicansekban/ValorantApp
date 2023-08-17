package com.example.composestarter.domain.mapper

import com.example.composestarter.data.model.agent.AgentResponse
import com.example.composestarter.domain.model.AbilitiesUIModel
import com.example.composestarter.domain.model.AgentsUIModel
import com.example.composestarter.domain.model.MediaListItemUIModel
import com.example.composestarter.domain.model.RoleUIModel
import com.example.composestarter.domain.model.VoiceLineUIModel
import javax.inject.Inject

class AgentsMapper @Inject constructor() {

    fun mapAgentResponseToUIModel(agentResponse: AgentResponse): AgentsUIModel {
        val roleUIModel = agentResponse.role?.let { role ->
            RoleUIModel(
                displayIcon = role.displayIcon,
                displayName = role.displayName,
                assetPath = role.assetPath,
                description = role.description,
                uuid = role.uuid
            )
        }

        val abilitiesUIModels = agentResponse.abilities?.map { ability ->
            AbilitiesUIModel(
                displayIcon = ability?.displayIcon,
                displayName = ability?.displayName,
                description = ability?.description,
                slot = ability?.slot
            )
        }

        val voiceLineUIModel = agentResponse.voiceLine?.let { voiceLine ->
            VoiceLineUIModel(
                minDuration = voiceLine.minDuration,
                mediaList = voiceLine.mediaList?.map { mediaListItem ->
                    MediaListItemUIModel(
                        id = mediaListItem?.id,
                        wave = mediaListItem?.wave,
                        wwise = mediaListItem?.wwise
                    )
                },
                maxDuration = voiceLine.maxDuration
            )
        }

        return AgentsUIModel(
            killfeedPortrait = agentResponse.killfeedPortrait,
            role = roleUIModel,
            isFullPortraitRightFacing = agentResponse.isFullPortraitRightFacing,
            displayName = agentResponse.displayName,
            isBaseContent = agentResponse.isBaseContent,
            description = agentResponse.description,
            backgroundGradientColors = agentResponse.backgroundGradientColors,
            isAvailableForTest = agentResponse.isAvailableForTest,
            uuid = agentResponse.uuid,
            characterTags = agentResponse.characterTags,
            displayIconSmall = agentResponse.displayIconSmall,
            fullPortrait = agentResponse.fullPortrait,
            fullPortraitV2 = agentResponse.fullPortraitV2,
            abilities = abilitiesUIModels,
            displayIcon = agentResponse.displayIcon,
            bustPortrait = agentResponse.bustPortrait,
            background = agentResponse.background,
            assetPath = agentResponse.assetPath,
            voiceLine = voiceLineUIModel,
            isPlayableCharacter = agentResponse.isPlayableCharacter,
            developerName = agentResponse.developerName
        )
    }
}