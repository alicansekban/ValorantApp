package com.example.composestarter.presentation.weapons.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.weapons.WeaponsUIModel
import com.example.composestarter.domain.usecase.weapons.WeaponDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeaponDetailViewModel @Inject constructor(
    private val useCase: WeaponDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _weapon = MutableStateFlow<BaseUIModel<WeaponsUIModel>>(Loading())
    val weapon: StateFlow<BaseUIModel<WeaponsUIModel>> get() = _weapon

    val id = checkNotNull(savedStateHandle.get<String>("id"))

    init {
        // savedStateHandle aracılığı ile argümana ulaşıp veri istek işlemini buradan yönetiyoruz, ui'a sadece sonucu gönderiyoruz.
        getWeaponDetail(id)
    }

    private fun getWeaponDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke(id).collect {
                _weapon.emit(it)
            }
        }
    }
}