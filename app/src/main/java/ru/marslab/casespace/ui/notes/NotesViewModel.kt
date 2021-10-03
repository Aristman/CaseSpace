package ru.marslab.casespace.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.domain.interactor.NotesInteractor
import ru.marslab.casespace.ui.mapper.toDomain
import ru.marslab.casespace.ui.mapper.toUi
import ru.marslab.casespace.ui.model.NoteUi
import ru.marslab.casespace.ui.notes.adapter.NoteListItem
import ru.marslab.casespace.ui.util.ViewState
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesInteractor: NotesInteractor
) : ViewModel() {
    private var _notes: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val notes: StateFlow<ViewState>
        get() = _notes
    private val notesList: MutableList<NoteListItem> = mutableListOf()

    fun getAllNotes() {
        _notes.value = ViewState.Loading
        viewModelScope.launch(notesInteractor.dispatchers.io) {
            try {
                if (notesList.isNullOrEmpty()) {
                    notesList.addAll(
                        notesInteractor.getAllNotes()
                            .map { NoteListItem.Note(it.toUi(), isExpand = false) }
                    )
                }
                _notes.emit(ViewState.Successful(getFullNoteList()))
            } catch (e: Exception) {
                _notes.emit(ViewState.LoadError(e))
            }
        }
    }

    fun addNewNote(note: NoteUi): Int {
        notesList.add(NoteListItem.Note(note, isExpand = false))
        _notes.value = ViewState.Successful(getFullNoteList())
        viewModelScope.launch(notesInteractor.dispatchers.io)
        {
            notesInteractor.addNewNote(note.toDomain())
        }
        return notesList.size.minus(1)
    }


    private fun getFullNoteList(): List<NoteListItem> {
        val allNotes = mutableListOf<NoteListItem>()
        return allNotes.apply {
            add(NoteListItem.Header)
            addAll(notesList)
            add(NoteListItem.Footer)
        }
    }

    fun clickOnItem(clickedItem: NoteListItem) {
        val clickedIndex = notesList.indexOf(clickedItem)
        notesList.removeAt(clickedIndex).apply {
            notesList.add(
                clickedIndex,
                (this as NoteListItem.Note).copy(isExpand = !isExpand)
            )
        }
        _notes.value = ViewState.Successful(getFullNoteList())
    }
}