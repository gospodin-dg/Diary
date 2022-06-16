package com.example.diary.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.diary.database.NotesRepository
import java.util.*

class ReadNoteViewModel: ViewModel() {
    private val repository: NotesRepository = NotesRepository.getRepository()
    private val noteIdLiveData = MutableLiveData<UUID>()

    var noteLiveData = Transformations.switchMap(noteIdLiveData) { noteId ->
        repository.getNote(noteId)
    }

    fun loadNote(noteId: UUID) {
        noteIdLiveData.value = noteId
    }

}