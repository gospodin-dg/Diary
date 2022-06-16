package com.example.diary.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.diary.database.NotesRepository
import com.example.diary.models.Note
import java.util.*

class UpdateNoteViewModel: ViewModel() {
    private val repository: NotesRepository = NotesRepository.getRepository()
    private val noteIdLiveData = MutableLiveData<UUID>()

    var noteLiveData = Transformations.switchMap(noteIdLiveData) {
        repository.getNote(it)
    }

    fun loadNote(noteId: UUID) {
        noteIdLiveData.value = noteId
    }

    fun saveNote (note: Note) {
        repository.updateNote(note)
    }

}