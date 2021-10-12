package ru.marslab.casespace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    value = "SELECT n.id, n.title, c.name, n.description FROM notes n, categories c WHERE n.categoryId=c.id",
    viewName = "note_details"
)
data class NoteDetailsDBView(
    val id: String,
    val title: String,
    @ColumnInfo(name = "name")
    val category: String,
    val description: String
)
