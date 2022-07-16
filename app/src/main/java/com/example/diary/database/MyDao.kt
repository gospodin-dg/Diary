package com.example.diary.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diary.models.Note
import java.util.*

@Dao
interface MyDao {

    @Query("SELECT * FROM notes WHERE id = (:id)")
    fun getNote(id: UUID): LiveData<Note?>

    @Query("SELECT * FROM notes")
    fun getAllNotesList(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE :title ")
    fun getSearchNotesList(title: String?): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

}