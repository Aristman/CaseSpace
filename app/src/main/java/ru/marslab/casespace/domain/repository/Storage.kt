package ru.marslab.casespace.domain.repository

import ru.marslab.casespace.domain.model.Note

interface Storage {
    fun getNasaApikey(): String
    fun saveTheme(theme: Int)
    fun getTheme(): Int

    suspend fun getAllNotes(): List<Note>
}