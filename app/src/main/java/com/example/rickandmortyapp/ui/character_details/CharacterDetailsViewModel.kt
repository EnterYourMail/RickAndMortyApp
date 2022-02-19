package com.example.rickandmortyapp.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.repository.Repository
import com.example.rickandmortyapp.utils.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val id: Int
) : ViewModel() {

    val viewState: StateFlow<ScreenState<CharacterDetails>>
        get() = _viewState
    private val _viewState = MutableStateFlow<ScreenState<CharacterDetails>>(
        ScreenState.Loading
    )

    init {
        retry()
    }

    fun retry() {
        viewModelScope.launch {
            _viewState.emit(ScreenState.Loading)
            delay(1000) // For test
            _viewState.emit(repository.characterDetails(id))
        }
    }

    @AssistedFactory
    interface Factory {
        fun get(id: Int): CharacterDetailsViewModel
    }

}