package com.example.rickandmortyapp.model

import com.google.gson.annotations.SerializedName

data class CharacterDetails(
    val id: Int = -1,
    val name: String = "",
    val status: String = "",
    val type: String = "",
    val location: Location = Location(""),
    val image: String = "",
    @SerializedName("episode")
    val episodesUrls: List<String> = listOf()
)
