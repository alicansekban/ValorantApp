package com.example.composestarter.presentation.favorites.skins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.data.local.model.skins.FavoriteSkinsEntity
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.usecase.favorites.skins.GetFavoriteSkinsUseCase
import com.example.composestarter.domain.usecase.favorites.skins.RemoveFavoriteSkinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteSkinsViewModel @Inject constructor(
    private val useCase: GetFavoriteSkinsUseCase,
    private val removeUseCase: RemoveFavoriteSkinUseCase
) : ViewModel() {

    private val _favoriteSkins = MutableStateFlow<BaseUIModel<List<FavoriteSkinsEntity>>>(Loading())
    val favoriteSkins: StateFlow<BaseUIModel<List<FavoriteSkinsEntity>>>
        get() = _favoriteSkins.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Loading()
        )
    private val _removeSkin = MutableStateFlow<BaseUIModel<Any>>(Loading())
    val removeSkin: StateFlow<BaseUIModel<Any>>
        get() = _removeSkin.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Loading()
        )

    init {
        getFavoriteSkins()
    }

    fun getFavoriteSkins(searchQuery: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            useCase(searchQuery).collect { value ->
                _favoriteSkins.emit(value)
            }
        }
    }


    fun removeFavoriteSkin(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeUseCase(id).collect {
                _removeSkin.emit(it)
            }
        }
    }

    fun removeFavoriteEmitted() {
        viewModelScope.launch {
            _removeSkin.emit(Loading())
        }
    }
}