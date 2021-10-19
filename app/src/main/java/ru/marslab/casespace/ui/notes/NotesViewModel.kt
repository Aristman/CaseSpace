package ru.marslab.casespace.ui.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.interactor.NotesInteractor
import ru.marslab.casespace.ui.mapper.toDomain
import ru.marslab.casespace.ui.mapper.toUi
import ru.marslab.casespace.ui.model.NoteUi
import ru.marslab.casespace.ui.notes.adapter.NoteListItem
import ru.marslab.casespace.ui.notes.adapter.NoteTouchHelperHandler
import ru.marslab.casespace.ui.util.ViewState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesInteractor: NotesInteractor,
    private val dispatchers: AppDispatchers
) : ViewModel(), NoteTouchHelperHandler {
    private var _notes: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val notes: StateFlow<ViewState>
        get() = _notes
    private val notesList: MutableList<NoteListItem> = mutableListOf()

    fun getAllNotes() {
        _notes.value = ViewState.Loading
        viewModelScope.launch(dispatchers.io) {
            try {
                if (notesList.isNullOrEmpty()) {
                    notesList.addAll(
                        notesInteractor.getAllNotes()
                            .map { NoteListItem.Note(it.toUi()) }
                    )
                }
                refreshNotesList()
            } catch (e: Exception) {
                _notes.emit(ViewState.LoadError(e))
            }
        }
    }

    fun addNewNote(note: NoteUi): Int {
        notesList.add(NoteListItem.Note(note))
        refreshNotesList()
        viewModelScope.launch(dispatchers.io)
        {
            notesInteractor.addNewNote(note.toDomain())
        }
        return notesList.size.minus(1)
    }


    fun clickOnItem(clickedItem: NoteListItem) {
        val clickedIndex = notesList.indexOf(clickedItem)
        notesList.removeAt(clickedIndex).apply {
            notesList.add(
                clickedIndex,
                (this as NoteListItem.Note).copy(isExpand = !isExpand)
            )
        }
        refreshNotesList()
    }

    override fun moveNote(fromPosition: Int, toPosition: Int) {
        Log.d("MY_TAG", "from - $fromPosition to - $toPosition")
        if (toPosition >= 0 && toPosition < notesList.size &&
            fromPosition >= 0 && fromPosition < notesList.size
        ) {
            Collections.swap(notesList, fromPosition, toPosition)
            refreshNotesList()
        }
    }

    override fun deleteNote(position: Int) {
        if (position >= 0 && position < notesList.size) {
            notesList.removeAt(position)
        }
        refreshNotesList()
    }


    private fun refreshNotesList() {
        _notes.value = ViewState.Successful(getFullNotesList())
    }


    private fun getFullNotesList(): List<NoteListItem> {
        val allNotes = mutableListOf<NoteListItem>()
        return allNotes.apply {
            add(NoteListItem.Header)
            addAll(notesList)
            add(NoteListItem.Footer)
        }
    }
}