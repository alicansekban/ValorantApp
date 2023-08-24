package com.example.composestarter.presentation.maps.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.MapsUIModel
import com.example.composestarter.domain.usecase.maps.MapDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsDetailViewModel @Inject constructor(
    private val mapDetailUseCase : MapDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _map = MutableStateFlow<BaseUIModel<MapsUIModel>>(Loading())
    val map: StateFlow<BaseUIModel<MapsUIModel>> get() = _map

    val id = checkNotNull(savedStateHandle.get<String>("id"))

    init {
        // savedStateHandle aracılığı ile argümana ulaşıp veri istek işlemini buradan yönetiyoruz, ui'a sadece sonucu gönderiyoruz.
        getMapDetail(id)
    }

    private fun getMapDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mapDetailUseCase(id).collect {
                _map.emit(it)
            }
        }
    }
}