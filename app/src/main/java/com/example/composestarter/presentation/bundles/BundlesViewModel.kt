package com.example.composestarter.presentation.bundles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composestarter.domain.BaseUIModel
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.model.bundles.BundlesUIModel
import com.example.composestarter.domain.usecase.bundles.BundlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class BundlesViewModel @Inject constructor(
    private val useCase : BundlesUseCase
) : ViewModel(){


    private val _bundles = MutableStateFlow<BaseUIModel<List<BundlesUIModel>>>(Loading())
    val bundles: StateFlow<BaseUIModel<List<BundlesUIModel>>> get() = _bundles

    init {
        getBundles()
    }

    fun getBundles() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke().collect { data ->
                _bundles.emit(data)
            }
        }
    }
}