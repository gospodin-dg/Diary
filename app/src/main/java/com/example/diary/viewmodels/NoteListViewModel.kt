package com.example.diary.viewmodels

import androidx.lifecycle.ViewModel
import com.example.diary.database.NotesRepository
import com.example.diary.models.Note
import java.util.*

private const val TAG = "NoteListViewModel"
class NoteListViewModel: ViewModel() {

    private val repository: NotesRepository = NotesRepository.getRepository()
    val notesListLiveData = repository.getNotesList()

    fun addNote(note: Note) {
        repository.createNote(note)
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }

    fun getNote(id: UUID) {
        repository.getNote(id)
    }

}