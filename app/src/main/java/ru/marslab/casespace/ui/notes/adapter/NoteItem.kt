package ru.marslab.casespace.ui.notes.adapter

import ru.marslab.casespace.ui.model.NoteUi

sealed class NoteItem(open val data: NoteUi? = null) {
    object Header : NoteItem()
    object Footer : NoteItem()
    data class CollapseNote(override val data: NoteUi) : NoteItem(data)
    data class ExpandNote(override val data: NoteUi) : NoteItem(data)
}
