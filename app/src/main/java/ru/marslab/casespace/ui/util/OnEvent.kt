package ru.marslab.casespace.ui.util

open class OnEvent<out T>(private val content: T) {
    var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}