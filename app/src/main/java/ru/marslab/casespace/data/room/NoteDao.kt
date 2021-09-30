package ru.marslab.casespace.data.room

import androidx.room.*
import ru.marslab.casespace.data.room.entity.NoteCategoryDB
import ru.marslab.casespace.data.room.entity.NoteDB
import ru.marslab.casespace.data.room.entity.NoteDetailsDBView

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<NoteDB>

    @Query("SELECT * FROM categories")
    fun getAllNoteCategories(): List<NoteCategoryDB>

    @Query("SELECT * FROM note_details WHERE id=:noteId")
    fun getNoteDetails(noteId: String): NoteDetailsDBView

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNewNote(note: NoteDB)

    @Update
    fun updateNote(note: NoteDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNewCategory(category: NoteCategoryDB)

    @Update
    fun updateCategory(category: NoteCategoryDB)
}