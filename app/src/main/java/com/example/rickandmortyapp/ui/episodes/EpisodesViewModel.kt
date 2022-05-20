package com.example.rickandmortyapp.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.Episode
import com.example.rickandmortyapp.repository.Repository
import com.example.rickandmortyapp.utils.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodesViewModel @AssistedInject constructor(
    private val repository: Repository,
    @Assisted private val episodes: IntArray
) : ViewModel() {

    val screenState: StateFlow<ScreenState<List<Episode>>>
        get() = _screenState
    private val _screenState = MutableStateFlow<ScreenState<List<Episode>>>(
        ScreenState.Loading
    )

    init {
        retry()
    }

    fun retry() {
        viewModelScope.launch {
            _screenState.emit(ScreenState.Loading)
            //delay(500) //For test
            _screenState.emit(repository.episodes(episodes))
        }
    }

    @AssistedFactory
    interface Factory {
        fun get(episodesArray: IntArray): EpisodesViewModel
    }

}