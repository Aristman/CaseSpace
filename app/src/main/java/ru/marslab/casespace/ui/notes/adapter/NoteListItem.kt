package ru.marslab.casespace.ui.notes.adapter

import ru.marslab.casespace.ui.model.NoteUi

sealed class NoteListItem() {
    object Header : NoteListItem()
    object Footer : NoteListItem()
    data class Note(
        val data: NoteUi,
        val isExpand: Boolean = false,
        val isSelected: Boolean = false
    ) : NoteListItem()
}
