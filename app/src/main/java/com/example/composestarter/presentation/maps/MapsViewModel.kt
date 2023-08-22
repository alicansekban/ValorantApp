package com.example.composestarter.presentation.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.MapsUIModel
import com.example.composestarter.domain.usecase.maps.MapsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val useCase: MapsUseCase) : ViewModel() {

    private val _maps = MutableStateFlow<BaseUIModel<List<MapsUIModel>>>(Loading())
    val maps: StateFlow<BaseUIModel<List<MapsUIModel>>> get() = _maps

    init {
        getAgents()
    }

    fun getAgents() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke().collect { data ->
                _maps.emit(data)
            }
        }
    }

    fun updateMaps() {

    }
 }