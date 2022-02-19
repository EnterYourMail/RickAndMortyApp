package com.example.rickandmortyapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortyapp.repository.Repository
import javax.inject.Inject

class CharactersViewModel @Inject constructor(repository: Repository) :
    ViewModel() {

    val characters = repository.characters().cachedIn(viewModelScope)

    /*class Factory @Inject constructor(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == CharactersViewModel::class.java) {
                @Suppress("UNCHECKED_CAST")
                return CharactersViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }*/
}