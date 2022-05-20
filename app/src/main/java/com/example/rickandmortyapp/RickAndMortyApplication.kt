package com.example.rickandmortyapp

import android.app.Application
import com.example.rickandmortyapp.di.AppComponent
import com.example.rickandmortyapp.di.DaggerAppComponent

class RickAndMortyApplication: Application() {
    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }
}