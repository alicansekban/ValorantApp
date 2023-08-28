package com.example.composestarter.presentation.seasons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.seasons.SeasonsUIModel
import com.example.composestarter.domain.usecase.seasons.SeasonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SeasonsViewModel @Inject constructor(
    private val useCase: SeasonsUseCase
) : ViewModel() {
    private val _seasons = MutableStateFlow<BaseUIModel<List<SeasonsUIModel>>>(Loading())
    val seasons: StateFlow<BaseUIModel<List<SeasonsUIModel>>> get() = _seasons

    init {
        getCompetitiveSeasons()
    }

    private fun getCompetitiveSeasons() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke().collect { data ->
                _seasons.emit(data)
            }
        }
    }
}