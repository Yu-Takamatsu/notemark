package com.yu.pl.app.challeng.notemark.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yu.pl.app.challeng.notemark.core.model.Note
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.UUID

@Entity(tableName = "NoteMark")
data class NoteMarkEntity(
    @PrimaryKey val id:String,
    val title:String,
    val content:String,
    val createdAt:Long,
    val lastEditedAt:Long,
    val isDelete: Boolean
)


fun NoteMarkEntity.toNoteMark(): Note{

    return Note(
        id = UUID.fromString(this.id),
        title = this.title,
        content = this.content,
        createdAt = OffsetDateTime.ofInstant(Instant.ofEpochSecond(createdAt), ZoneId.systemDefault()),
        lastEditedAt = OffsetDateTime.ofInstant(Instant.ofEpochSecond(lastEditedAt), ZoneId.systemDefault())
    )
}

fun Note.toNoteMarkEntity(): NoteMarkEntity {
    return NoteMarkEntity(
        id = this.id.toString(),
        title = this.title,
        content = this.content,
        createdAt = this.createdAt.toEpochSecond(),
        lastEditedAt = this.lastEditedAt.toEpochSecond(),
        isDelete = false
    )
}

