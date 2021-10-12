package ru.marslab.casespace.ui.notes.adapter

interface NoteTouchHelperHandler {
    fun moveNote(fromPosition: Int, toPosition: Int)
    fun deleteNote(position: Int)
}