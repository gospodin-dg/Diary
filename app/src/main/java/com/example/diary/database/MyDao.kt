package com.example.diary.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.diary.models.Note

interface MyDao {

    @Query("SELECT * FROM notes WHERE id = (:id)")
    fun getNote(id: Int): LiveData<Note?>

    @Query("SELECT * FROM notes")
    fun getNotesList(): LiveData<List<Note>>

    @Insert
    fun createNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

}