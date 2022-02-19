package com.example.rickandmortyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyapp.api.RickAndMortyApi
import com.example.rickandmortyapp.model.Character
import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.model.Episode
import com.example.rickandmortyapp.utils.ScreenState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val service: RickAndMortyApi) {

    fun characters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_AT_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharactersPagingSource(service) }
        ).flow
    }

    suspend fun characterDetails(id: Int): ScreenState<CharacterDetails> {
        return try {
            ScreenState.Content(service.characterDetails(id))
        } catch (e: Throwable) {
            ScreenState.Error(e)
        }
    }

    suspend fun episodes(episodes: IntArray): ScreenState<List<Episode>> {
        return try {
            when {
                episodes.isEmpty() -> ScreenState.Content(listOf())
                episodes.size == 1 -> ScreenState.Content(
                    listOf(service.oneEpisode(episodes[0]))
                )

                else -> {
                    ScreenState.Content(
                        service.multipleEpisodes(episodes.joinToString(","))
                    )
                }
            }
        } catch (e:Throwable) {
            ScreenState.Error(e)
        }
    }

    companion object {
        const val ITEMS_AT_PAGE = 20
    }
}