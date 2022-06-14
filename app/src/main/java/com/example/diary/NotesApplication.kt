package com.example.diary


import android.app.Application
import android.util.Log
import com.example.diary.database.NotesRepository

private const val TAG = "NotesApplication"

class NotesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NotesRepository.createRepository(this)
    }
}