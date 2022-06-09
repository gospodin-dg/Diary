package com.example.diary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diary.models.Note

@Database(entities = [Note::class], version = 1)
abstract class MyDataBase: RoomDatabase() {
    abstract fun getDao(): MyDao
}