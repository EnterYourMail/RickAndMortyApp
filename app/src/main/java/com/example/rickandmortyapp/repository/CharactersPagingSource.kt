package com.example.rickandmortyapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.api.RickAndMortyApi
import com.example.rickandmortyapp.model.Character
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(private val service: RickAndMortyApi) :
    PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: 0
        return try {
            val response = service.charactersPage(position)
            val nextKey = response.pagesInfo.next?.let { position + 1 }
            val prevKey = response.pagesInfo.prev?.let { position - 1 }
            LoadResult.Page(response.result, nextKey, prevKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}