package ru.marslab.casespace.domain.repository

interface Storage {
    fun getNasaApikey(): String
    fun saveTheme(theme: Int)
    fun getTheme(): Int
}