package com.example.rickandmortyapp.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class EpisodesViewModel(repository: Repository, arrayString: String) : ViewModel() {

    val viewState = flow { emit(repository.episodes(arrayString)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    @AssistedFactory
    interface AssistedViewModelFactory {
        fun create(arrayString: String): Factory
    }

    class Factory @AssistedInject constructor(
        private val repository: Repository,
        @Assisted private val arrayString: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(EpisodesViewModel::class.java)) {
                return EpisodesViewModel(repository, arrayString) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class!")
            }
        }
    }

}