package com.example.rickandmortyapp.di

import com.example.rickandmortyapp.ui.character_details.CharacterDetailsFragment
import com.example.rickandmortyapp.ui.characters.CharactersFragment
import com.example.rickandmortyapp.ui.episodes.EpisodesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules=[StorageModule::class])
interface AppComponent {

    /*@Component.Factory
    interface Factory {
        fun create(): AppComponent
    }*/

    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharacterDetailsFragment)
    fun inject(fragment: EpisodesFragment)
}