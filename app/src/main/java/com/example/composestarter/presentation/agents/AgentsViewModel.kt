package com.example.composestarter.presentation.agents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.AgentsUIModel
import com.example.composestarter.domain.usecase.AgentsUseCase
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
    private val useCase: AgentsUseCase
) : ViewModel() {
    private val _agents = MutableStateFlow<BaseUIModel<List<AgentsUIModel>>>(Loading())
    val agents: StateFlow<BaseUIModel<List<AgentsUIModel>>> get() = _agents

    init {
        getAgents()
    }

    fun getAgents() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke().collect { data ->
                _agents.emit(data)
            }
        }
    }
}