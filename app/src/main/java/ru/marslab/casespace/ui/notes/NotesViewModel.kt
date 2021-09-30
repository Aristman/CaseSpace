package ru.marslab.casespace.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.domain.interactor.NotesInteractor
import ru.marslab.casespace.ui.mapper.toUi
import ru.marslab.casespace.ui.util.ViewState
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesInteractor: NotesInteractor
) : ViewModel() {
    private var _notes: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Init)
    val notes: StateFlow<ViewState>
        get() = _notes

    fun getAllNotes() {
        _notes.value = ViewState.Loading
        viewModelScope.launch(notesInteractor.dispatchers.io) {
            try {
                val allNotes = notesInteractor.getAllNotes().map { it.toUi() }
                _notes.emit(ViewState.Successful(allNotes))
            } catch (e: Exception) {
                _notes.emit(ViewState.LoadError(e))
            }
        }
    }
}