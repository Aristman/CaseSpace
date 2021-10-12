package ru.marslab.casespace.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.marslab.casespace.data.room.entity.NoteCategoryDB
import ru.marslab.casespace.data.room.entity.NoteDB
import ru.marslab.casespace.data.room.entity.NoteDetailsDBView

@Database(
    entities = [NoteDB::class, NoteCategoryDB::class],
    views = [NoteDetailsDBView::class],
    version = 1
)
abstract class CaseSpaceDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}