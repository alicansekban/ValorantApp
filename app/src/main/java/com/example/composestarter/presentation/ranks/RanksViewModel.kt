package com.example.composestarter.presentation.ranks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.ranks.RanksUIModel
import com.example.composestarter.domain.usecase.ranks.RanksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RanksViewModel @Inject constructor(
    private val useCase: RanksUseCase
) : ViewModel(){

    private val _ranks = MutableStateFlow<BaseUIModel<List<RanksUIModel>>>(Loading())
    val ranks: StateFlow<BaseUIModel<List<RanksUIModel>>> get() = _ranks

    init {
        getRanks()
    }

    fun getRanks() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke().collect { data ->
                _ranks.emit(data)
            }
        }
    }
}