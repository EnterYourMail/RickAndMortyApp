package com.example.rickandmortyapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortyapp.repository.Repository
import javax.inject.Inject

class CharactersViewModel(repository: Repository): ViewModel() {

    val characters = repository.characters().cachedIn(viewModelScope)

    class Factory @Inject constructor(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
                return CharactersViewModel(repository) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class!")
            }
        }
    }
}