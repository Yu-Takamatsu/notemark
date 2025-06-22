package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.TypeConverter

class NoteMarkTypeConverter {
    @TypeConverter
    fun fromSyncStatus(status: SyncStatus): String {
        return status.name
    }

    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus {
        return SyncStatus.valueOf(value)
    }
}