package com.example.rickandmortyapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortyapp.repository.Repository
import javax.inject.Inject

class CharactersViewModel @Inject constructor(repository: Repository) :
    ViewModel() {

    val characters = repository.characters().cachedIn(viewModelScope)

    /*val screenState: StateFlow<ScreenState<Int>>
        get() = _screenState
    private val _screenState = MutableStateFlow<ScreenState<Int>>(
        ScreenState.Loading
    )

    fun setLoadingState() {
        viewModelScope.launch {
            _screenState.emit(ScreenState.Loading)
        }
    }

    fun setContentState() {
        viewModelScope.launch {
            _screenState.emit(ScreenState.Content(0))
        }
    }

    private fun setErrorState(e: Throwable) {
        viewModelScope.launch {
            _screenState.emit(ScreenState.Error(e))
        }
    }*/

}