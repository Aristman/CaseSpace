package ru.marslab.casespace.ui.util

sealed class ViewState {
    data class Successful<out T>(val data: T) : ViewState()
    data class LoadError(val error: Throwable) : ViewState()
    object Loading : ViewState()
}