package com.example.rickandmortyapp.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.repository.Repository
import com.example.rickandmortyapp.ui.characters.CharactersViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CharacterDetailsViewModel(repository: Repository, id: Int) : ViewModel() {

    val viewState = flow<CharacterDetails> { repository.characterDetails(id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), CharacterDetails())

    class Factory(private val repository: Repository, private val id: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
                return CharacterDetailsViewModel(repository, id) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class!")
            }
        }
    }
}