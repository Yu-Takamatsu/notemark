package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteMarkEntity::class], version = 1)
@TypeConverters(NoteMarkTypeConverter::class)
abstract class NoteMarkDatabase: RoomDatabase()  {
    abstract val noteMarkDao: NoteMarkDao
}