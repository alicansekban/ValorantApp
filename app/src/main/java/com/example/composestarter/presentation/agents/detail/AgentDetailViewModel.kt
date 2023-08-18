package com.example.composestarter.presentation.agents.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.AgentsUIModel
import com.example.composestarter.domain.usecase.agents.AgentDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgentDetailViewModel @Inject constructor(
    private val agentDetailUseCase: AgentDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _agent = MutableStateFlow<BaseUIModel<AgentsUIModel>>(Loading())
    val agent: StateFlow<BaseUIModel<AgentsUIModel>> get() = _agent

    val id = checkNotNull(savedStateHandle.get<String>("id"))

    init {
        // savedStateHandle aracılığı ile argümana ulaşıp veri istek işlemini buradan yönetiyoruz, ui'a sadece sonucu gönderiyoruz.
        getAgentDetail(id)
    }

    private fun getAgentDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            agentDetailUseCase(id).collect {
                _agent.emit(it)
            }
        }
    }
}