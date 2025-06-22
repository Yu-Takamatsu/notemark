package com.yu.pl.app.challeng.notemark.functions.data.note

import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMarkList
import kotlinx.serialization.Serializable

@Serializable
data class NoteMarkResponse(
    val notes:List<NoteMarkDto>,
    val total:Int
)

fun NoteMarkResponse.toNoteMarList(): NoteMarkList{
    return NoteMarkList(
        notes = this.notes.map {
            it.toNoteMark()
        },
        totalCount = this.total
    )
}
