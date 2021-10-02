package ru.marslab.casespace.domain.interactor.impl

import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.interactor.NotesInteractor
import ru.marslab.casespace.domain.model.Note
import ru.marslab.casespace.domain.repository.Storage


class NotesInteractorImpl(
    private val storage: Storage,
    override val dispatchers: AppDispatchers
) : NotesInteractor {

    override suspend fun getAllNotes(): List<Note> =
        storage.getAllNotes()

    override suspend fun addNewNote(note: Note) {
        storage.saveNewNote(note)
    }
}