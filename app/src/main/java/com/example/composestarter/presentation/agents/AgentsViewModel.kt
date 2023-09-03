package com.example.composestarter.presentation.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.data.local.model.agents.FavoriteAgentsEntity
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.agents.AgentsUIModel
import com.example.composestarter.domain.usecase.agents.AgentsUseCase
import com.example.composestarter.domain.usecase.favorites.agents.AddFavoriteAgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgentsViewModel @Inject constructor(
    private val useCase: AgentsUseCase,
    private val agentUseCase: AddFavoriteAgentUseCase
) : ViewModel() {
    private val _agents = MutableStateFlow<BaseUIModel<List<AgentsUIModel>>>(Loading())
    val agents: StateFlow<BaseUIModel<List<AgentsUIModel>>> get() = _agents

    private val _favoriteAgent = MutableStateFlow<BaseUIModel<Any>>(Loading())
    val favoriteAgent: StateFlow<BaseUIModel<Any>>
        get() = _favoriteAgent.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Loading()
        )

    init {
        getAgents()
    }

    private fun getAgents() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke().collect { data ->
                _agents.emit(data)
            }
        }
    }

    fun addAgentToFavorite(model: AgentsUIModel, id: String, voiceLine: String) {
        val requestModel = FavoriteAgentsEntity(
            id = id,
            displayName = model.displayName ?: "Unknown Agent",
            role = model.role?.displayName ?: "Unknown Role",
            roleDisplayIcon = model.role?.displayIcon.toString(),
            fullPortrait = model.fullPortrait.toString(),
            displayIcon = model.displayIcon.toString(),
            description = model.description ?: "Useless Agent :)",
            voiceLine = voiceLine
        )
        viewModelScope.launch(Dispatchers.IO) {
            agentUseCase(requestModel).collect {
                _favoriteAgent.emit(it)
            }
        }
    }

    fun favoriteEmitted() {
        viewModelScope.launch {
            _favoriteAgent.emit(Loading())
        }
    }
}