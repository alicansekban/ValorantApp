package com.example.composestarter.presentation.favorites.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.data.local.model.agents.FavoriteAgentsEntity
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.usecase.favorites.agents.GetFavoriteAgentsUseCase
import com.example.composestarter.domain.usecase.favorites.agents.RemoveFavoriteAgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteAgentsViewModel @Inject constructor(
    private val useCase: GetFavoriteAgentsUseCase,
    private val removeUseCase : RemoveFavoriteAgentUseCase
) : ViewModel() {

    private val _favoriteAgents =
        MutableStateFlow<BaseUIModel<List<FavoriteAgentsEntity>>>(Loading())
    val favoriteAgents: StateFlow<BaseUIModel<List<FavoriteAgentsEntity>>>
        get() = _favoriteAgents.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Loading()
        )

    private val _removeAgent = MutableStateFlow<BaseUIModel<Any>>(Loading())
    val removeAgent: StateFlow<BaseUIModel<Any>>
        get() = _removeAgent.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Loading()
        )

    init {
        getFavoriteAgents()
    }

    fun getFavoriteAgents() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase().collect { value ->
                _favoriteAgents.emit(value)
            }
        }
    }

    fun removeFavoriteAgent(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeUseCase(id).collect {
                _removeAgent.emit(it)
            }
        }
    }
    fun removeFavoriteEmitted() {
        viewModelScope.launch {
            _removeAgent
                .emit(Loading())
        }
    }
}