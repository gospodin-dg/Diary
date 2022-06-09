package com.example.diary.database

import androidx.room.TypeConverter
import java.util.*

class TypesConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millSec: Long?): Date? {
        return millSec?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(str: String?): UUID? {
        return UUID.fromString(str)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}