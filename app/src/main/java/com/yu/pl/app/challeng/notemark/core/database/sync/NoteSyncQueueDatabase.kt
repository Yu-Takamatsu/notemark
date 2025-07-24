package com.yu.pl.app.challeng.notemark.core.database.sync

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteSyncQueueEntity::class], version = 1)
@TypeConverters(NoteSyncQueueTypeConverter::class)
abstract class NoteSyncQueueDatabase: RoomDatabase()  {
    abstract val syncQueueDao: NoteSyncQueueDao
}