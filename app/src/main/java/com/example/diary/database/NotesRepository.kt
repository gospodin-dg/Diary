package com.example.diary.database

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diary.models.Note
import kotlinx.coroutines.newSingleThreadContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "Notes"

class NotesRepository (context: Context) {

    private val executor: Executor = Executors.newSingleThreadExecutor()

    companion object {
        private var INSTANCE: NotesRepository? = null
        private fun createRepository(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NotesRepository(context)
            }
        }
        private fun getRepository(): NotesRepository {
            return INSTANCE?: throw IllegalStateException("Repository must be initialized")
        }
    }

    private val myDataBase = Room.databaseBuilder(
        context,
        MyDataBase::class.java,
        DATABASE_NAME
    ).build()

    private val myDao = myDataBase.getDao()

    private fun getNotesList(): LiveData<List<Note>> {
        return myDao.getNotesList()
    }

    private fun getNote(id: Int): LiveData<Note?> {
        return myDao.getNote(id)
    }

    private fun createNote(note: Note) {
        executor.execute {
            myDao.createNote(note)
        }
    }

    private fun updateNote(note: Note) {
        executor.execute {
            myDao.updateNote(note)
        }
    }

    private fun deleteNote(note: Note) {
        executor.execute {
            myDao.deleteNote(note)
        }
    }

}