package com.example.rickandmortyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyapp.api.RickAndMortyApi
import com.example.rickandmortyapp.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val service: RickAndMortyApi) {

    fun characters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                service.itemsAtPage(),
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharactersPagingSource(service) }
        ).flow
    }
    suspend fun characterDetails(id: Int) = service.characterDetails(id)

    suspend fun episodes(arrayString: String) = service.multipleEpisodes(arrayString)
}