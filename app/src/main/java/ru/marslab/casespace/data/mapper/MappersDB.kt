package ru.marslab.casespace.data.mapper

import ru.marslab.casespace.data.room.entity.NoteCategoryDB
import ru.marslab.casespace.data.room.entity.NoteDB
import ru.marslab.casespace.data.room.entity.NoteDetailsDBView
import ru.marslab.casespace.domain.model.Note
import ru.marslab.casespace.domain.model.NoteCategory

fun NoteDB.toDomain(): Note =
    Note(
        id = id,
        title = title,
        categoryId = categoryId,
        description = description
    )

fun NoteDetailsDBView.toDomain(): Note =
    Note(
        id = id,
        title = title,
        description = description,
        categoryName = category
    )

fun NoteCategoryDB.toDomain(): NoteCategory =
    NoteCategory(id, name)

fun Note.toDB(): NoteDB =
    NoteDB(
        id = id,
        title = title,
        categoryId = categoryId ?: "",
        description = description
    )