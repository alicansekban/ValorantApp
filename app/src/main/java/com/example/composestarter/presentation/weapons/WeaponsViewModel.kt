package com.example.composestarter.presentation.weapons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.WeaponsUIModel
import com.example.composestarter.domain.usecase.weapons.WeaponsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeaponsViewModel @Inject constructor(
    private val useCase: WeaponsUseCase
) : ViewModel() {


    private val _weapons = MutableStateFlow<BaseUIModel<List<WeaponsUIModel>>>(Loading())
    val weapons: StateFlow<BaseUIModel<List<WeaponsUIModel>>> get() = _weapons

    init {
        getWeapons()
    }

    fun getWeapons() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke().collect { data ->
                _weapons.emit(data)
            }
        }
    }
}