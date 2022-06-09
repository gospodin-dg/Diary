package com.example.diary.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note (@PrimaryKey val id: Int,
            var title: String = "",
            var noteText: String = "",
            var date: Date = Date()
)