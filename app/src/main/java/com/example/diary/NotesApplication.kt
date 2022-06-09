package com.example.diary

import android.app.Application
import com.example.diary.database.NotesRepository

class NotesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NotesRepository.newInstance(this)
    }
}