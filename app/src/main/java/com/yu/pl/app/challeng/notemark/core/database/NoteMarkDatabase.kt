package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteMarkEntity::class], version = 1)
abstract class NoteMarkDatabase: RoomDatabase()  {
    abstract val noteMarkDao: NoteMarkDao
}