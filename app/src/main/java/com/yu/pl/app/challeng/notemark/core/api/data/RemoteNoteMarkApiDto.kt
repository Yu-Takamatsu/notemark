package com.yu.pl.app.challeng.notemark.core.api.data

import com.yu.pl.app.challeng.notemark.core.model.Note
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Serializable
data class RemoteNoteMarkApiDto(
    val id:String,
    val title:String,
    val content:String,
    val createdAt:String,
    val lastEditedAt:String
)

@Serializable
data class RemoteNoteMarkListApiDto(
    val notes:List<RemoteNoteMarkApiDto>,
    val total:Int
)

fun RemoteNoteMarkListApiDto.toNoteMarList(): List<Note>{
    return  this.notes.map {
            it.toNoteMark()
        }
}



fun Note.toRemoteNoteMarkApiDto(): RemoteNoteMarkApiDto{

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    return RemoteNoteMarkApiDto(
        id = this.id.toString(),
        title = this.title,
        content = this.content,
        createdAt = this.createdAt.format(formatter),
        lastEditedAt = this.lastEditedAt.format(formatter)
    )
}

fun RemoteNoteMarkApiDto.toNoteMark(): Note {
    return Note(
        id = UUID.fromString(this.id), // StringからUUIDへの変換
        title = this.title,
        content = this.content,
        createdAt = OffsetDateTime.parse(this.createdAt), // StringからOffsetDateTimeへの変換
        lastEditedAt = OffsetDateTime.parse(this.lastEditedAt)
    )
}