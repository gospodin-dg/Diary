package com.example.diary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diary.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(TypesConverters::class)
abstract class MyDataBase: RoomDatabase() {
    abstract fun getDao(): MyDao
}