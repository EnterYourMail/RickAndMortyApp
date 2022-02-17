package com.example.rickandmortyapp.ui.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CharacterDetailsViewModel(repository: Repository, id: Int) : ViewModel() {

    val viewState = flow { emit(repository.characterDetails(id)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), CharacterDetails())

    @AssistedFactory
    interface AssistedViewModelFactory {
        fun create(id: Int): Factory
    }

    class Factory @AssistedInject  constructor(
        private val repository: Repository,
        @Assisted private val id: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
                return CharacterDetailsViewModel(repository, id) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class!")
            }
        }
    }
}