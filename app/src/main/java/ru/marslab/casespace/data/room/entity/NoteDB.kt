package ru.marslab.casespace.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDB(
    @PrimaryKey
    val id: String,
    val title: String,
    val categoryId: String,
    val description: String
)
