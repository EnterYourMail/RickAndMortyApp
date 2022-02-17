package com.example.rickandmortyapp.api

import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.model.CharactersPage
import com.example.rickandmortyapp.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun charactersPage(@Query("page") page: Int = 1): CharactersPage

    @GET("character/{id}")
    suspend fun characterDetails(@Path("id") id: Int): CharacterDetails

    @GET("episode/{array}")
    suspend fun multipleEpisodes(@Path("array") arrayString: String): List<Episode>

}