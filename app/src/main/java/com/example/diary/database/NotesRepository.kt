package com.example.diary.database


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.diary.models.Note
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "Notes"
private const val TAG = "NotesRepository"

class NotesRepository private constructor (context: Context) {

    private val executor: Executor = Executors.newSingleThreadExecutor()

    companion object {
        private var INSTANCE: NotesRepository? = null
        fun createRepository(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NotesRepository(context)
            }
        }
        fun getRepository(): NotesRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }

    private val myDataBase = Room.databaseBuilder(
        context,
        MyDataBase::class.java,
        DATABASE_NAME
    ).build()

    private val myDao = myDataBase.getDao()

    fun getNotesList(): LiveData<List<Note>> {
        return myDao.getNotesList()
    }

    fun getNote(id: UUID): LiveData<Note?> {
        return myDao.getNote(id)
    }

    fun createNote(note: Note) {
        executor.execute {
            myDao.createNote(note)
        }
    }

    fun updateNote(note: Note) {
        executor.execute {
            myDao.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        executor.execute {
            myDao.deleteNote(note)
        }
    }

}