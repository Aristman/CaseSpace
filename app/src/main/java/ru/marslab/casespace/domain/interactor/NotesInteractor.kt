package ru.marslab.casespace.domain.interactor

import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.model.Note

interface NotesInteractor {
    val dispatchers: AppDispatchers

    suspend fun getAllNotes(): List<Note>
    suspend fun addNewNote(note: Note)
}