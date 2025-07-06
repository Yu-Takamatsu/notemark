package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMark
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "NoteMark")
data class NoteMarkEntity(
    @PrimaryKey val id:String,
    val title:String,
    val content:String,
    val createdAt:String,
    val lastEditedAt:String,
    val syncStatus: SyncStatus,
    val isDelete: Boolean
)

enum class SyncStatus{
    NOT_POSTED,
    NOT_UPDATED,
    SYNCED
}


fun NoteMarkEntity.toNoteMark(): NoteMark{

    return NoteMark(
        id = UUID.fromString(this.id),
        title = this.title,
        content = this.content,
        createdAt = OffsetDateTime.parse(this.createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
        lastEditedAt = OffsetDateTime.parse(this.lastEditedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    )
}

fun NoteMark.toNoteMarkEntity(): NoteMarkEntity {
    return NoteMarkEntity(
        id = this.id.toString(),
        title = this.title,
        content = this.content,
        createdAt = this.createdAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
        lastEditedAt = this.lastEditedAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
        syncStatus = SyncStatus.NOT_POSTED,
        isDelete = false
    )
}

