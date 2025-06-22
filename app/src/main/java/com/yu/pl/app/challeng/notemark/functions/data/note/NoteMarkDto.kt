package com.yu.pl.app.challeng.notemark.functions.data.note

import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMark
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.util.UUID

@Serializable
data class NoteMarkDto(
    val id:String,
    val title:String,
    val content:String,
    val createdAt:String,
    val lastEditedAt:String
)


fun NoteMark.toNoteMarkDto(): NoteMarkDto{
    return NoteMarkDto(
        id = this.id.toString(),
        title = this.title,
        content = this.content,
        createdAt = this.createdAt.toString(),
        lastEditedAt = this.lastEditedAt.toString()
    )
}

fun NoteMarkDto.toNoteMark(): NoteMark {
    return NoteMark(
        id = UUID.fromString(this.id), // StringからUUIDへの変換
        title = this.title,
        content = this.content,
        createdAt = OffsetDateTime.parse(this.createdAt), // StringからOffsetDateTimeへの変換
        lastEditedAt = OffsetDateTime.parse(this.lastEditedAt)
    )
}