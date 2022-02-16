package com.example.rickandmortyapp.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.repository.Repository
import com.example.rickandmortyapp.ui.characters.CharactersViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class EpisodesViewModel(repository: Repository, arrayString: String) : ViewModel() {

    val viewState = flow<CharacterDetails> { repository.episodes(arrayString) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), CharacterDetails())

    class Factory(private val repository: Repository, private val arrayString: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
                return EpisodesViewModel(repository, arrayString) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class!")
            }
        }
    }

}