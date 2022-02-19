package com.example.rickandmortyapp.utils

sealed class ScreenState<out T> {
    class Content<out T>(val data: T): ScreenState<T>()
    class Error(val exception: Throwable): ScreenState<Nothing>()
    object Loading: ScreenState<Nothing>()
}
