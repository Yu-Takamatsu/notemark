package com.yu.pl.app.challeng.notemark.core.database.sync

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yu.pl.app.challeng.notemark.core.model.Note

@Entity(tableName = "NoteSyncQueue")
data class NoteSyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val type: QueueType,
    val noteId: String,
    val note: Note,
    val timestamp: Long
)

enum class QueueType {
    Created,
    Updated,
    Deleted
}


fun Note.toNoteSyncQueueEntity(
    userId: String,
    type: QueueType
): NoteSyncQueueEntity {
    return NoteSyncQueueEntity(
        id = 0,
        userId = userId,
        type = type,
        noteId = this.id.toString(),
        timestamp = System.currentTimeMillis(),
        note = this,
    )
}
