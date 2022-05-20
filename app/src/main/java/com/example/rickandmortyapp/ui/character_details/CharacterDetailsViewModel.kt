package com.example.rickandmortyapp.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.repository.Repository
import com.example.rickandmortyapp.utils.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val id: Int
) : ViewModel() {

    val screenState: StateFlow<ScreenState<CharacterDetails>>
        get() = _screenState
    private val _screenState = MutableStateFlow<ScreenState<CharacterDetails>>(
        ScreenState.Loading
    )

    init {
        retry()
    }

    fun retry() {
        viewModelScope.launch {
            _screenState.emit(ScreenState.Loading)
            //delay(500) // For test
            _screenState.emit(repository.characterDetails(id))
        }
    }

    @AssistedFactory
    interface Factory {
        fun get(id: Int): CharacterDetailsViewModel
    }

}