package com.example.diary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.diary.database.NotesRepository
import com.example.diary.models.Note
import java.util.*

private const val TAG = "NoteListViewModel"
class NoteListViewModel: ViewModel() {

    private val repository: NotesRepository = NotesRepository.getRepository()
    val notesListLiveData = repository.getAllNotesList()

    fun addNote(note: Note) {
        repository.createNote(note)
    }

    fun searchNotes(title: String?): LiveData<List<Note>> {
       return repository.getSearchNotesList(title)
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }



}