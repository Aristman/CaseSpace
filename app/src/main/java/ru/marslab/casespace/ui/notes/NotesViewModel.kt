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
import ru.marslab.casespace.ui.notes.adapter.NoteItem
import ru.marslab.casespace.ui.util.ViewState
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesInteractor: NotesInteractor
) : ViewModel() {
    private var _notes: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val notes: StateFlow<ViewState>
        get() = _notes
    private val notesList: MutableList<NoteItem> = mutableListOf()

    fun getAllNotes() {
        _notes.value = ViewState.Loading
        viewModelScope.launch(notesInteractor.dispatchers.io) {
            try {
                if (notesList.isNullOrEmpty()) {
                    notesList.addAll(
                        notesInteractor.getAllNotes().map { NoteItem.CollapseNote(it.toUi()) }
                    )
                }
                _notes.emit(ViewState.Successful(getFullNoteList()))
            } catch (e: Exception) {
                _notes.emit(ViewState.LoadError(e))
            }
        }
    }

    fun addNewNote(note: NoteUi): Int {
        notesList.add(NoteItem.CollapseNote(note))
        _notes.value = ViewState.Successful(getFullNoteList())
        viewModelScope.launch(notesInteractor.dispatchers.io)
        {
            notesInteractor.addNewNote(note.toDomain())
        }
        return notesList.size.minus(1)
    }


    private fun getFullNoteList(): List<NoteItem> {
        val allNotes = mutableListOf<NoteItem>()
        return allNotes.apply {
            add(NoteItem.Header)
            addAll(notesList)
            add(NoteItem.Footer)
        }
    }

    fun clickOnItem(position: Int) {
        when (val clickedItem = notesList[position - 1]) {
            is NoteItem.CollapseNote -> {
                notesList.remove(clickedItem)
                notesList.add(position - 1, NoteItem.ExpandNote(clickedItem.data))
            }
            is NoteItem.ExpandNote -> {
                notesList.remove(clickedItem)
                notesList.add(position - 1, NoteItem.CollapseNote(clickedItem.data))
            }
            else -> {
            }
        }
        _notes.value = ViewState.Successful(getFullNoteList())
    }
}