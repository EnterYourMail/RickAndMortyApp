package com.example.rickandmortyapp.api

import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.model.Characters
import com.example.rickandmortyapp.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun charactersPage(@Query("page") page: Int = 1): Characters

    @GET("{id}")
    suspend fun characterDetails(@Path("id") id: Int): CharacterDetails

    suspend fun listEpisodes(list: Iterable<Int>): List<Episode> {
        return multipleEpisodes(list.joinToString(","))
    }

    @GET("{array}")
    suspend fun multipleEpisodes(@Path("array") arrayString: String): List<Episode>

}