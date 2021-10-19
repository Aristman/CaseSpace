package ru.marslab.casespace.domain.interactor

import ru.marslab.casespace.domain.model.Note
import ru.marslab.casespace.domain.repository.Storage


class NotesInteractorImpl(private val storage: Storage) : NotesInteractor {

    override suspend fun getAllNotes(): List<Note> =
        storage.getAllNotes()

    override suspend fun addNewNote(note: Note) {
        storage.saveNewNote(note)
    }
}