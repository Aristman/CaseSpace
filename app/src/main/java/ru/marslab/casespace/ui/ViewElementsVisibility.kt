package ru.marslab.casespace.ui

interface ViewElementsVisibility {
    fun toolbarVisibility(status: Boolean)
    fun wikiSearchVisibility(status: Boolean)
    fun buttonNavVisibility(status: Boolean)
}