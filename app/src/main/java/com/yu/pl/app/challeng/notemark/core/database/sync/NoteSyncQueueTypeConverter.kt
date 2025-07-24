package com.yu.pl.app.challeng.notemark.core.database.sync

import androidx.room.TypeConverter
import com.yu.pl.app.challeng.notemark.core.model.Note
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID

class NoteSyncQueueTypeConverter {
    @TypeConverter
    fun fromQueueType(type: QueueType): String {
        return type.name
    }

    @TypeConverter
    fun toQueueType(value: String): QueueType {
        return QueueType.valueOf(value)
    }

    @TypeConverter
    fun fromNoteMark(note:Note):String{
        return Json.encodeToString(note.toNoteData())
    }

    @TypeConverter
    fun toNoteMark(value:String): Note{
        return Json.decodeFromString<NoteData>(value).toNoteMark()
    }
}

@Serializable
private data class NoteData(
    val id:String,
    val title:String,
    val content:String,
    val createAt:Long,
    val editAt:Long
)

private fun Note.toNoteData(): NoteData{
    return NoteData(
        id = this.id.toString(),
        title = this.title,
        content = this.content,
        createAt = this.createdAt.toEpochSecond(),
        editAt = this.lastEditedAt.toEpochSecond()
    )
}

private fun NoteData.toNoteMark():Note{
    return Note(
        id = UUID.fromString(this.id),
        title = this.title,
        content = this.content,
        createdAt = OffsetDateTime.ofInstant(Instant.ofEpochSecond(this.createAt), ZoneOffset.UTC),
        lastEditedAt = OffsetDateTime.ofInstant(Instant.ofEpochSecond(this.editAt), ZoneOffset.UTC),
    )
}