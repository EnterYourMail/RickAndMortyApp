package com.example.rickandmortyapp.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules=[StorageModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }


}