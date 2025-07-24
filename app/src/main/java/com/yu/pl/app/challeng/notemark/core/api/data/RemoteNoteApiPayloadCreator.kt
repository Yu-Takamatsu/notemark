package com.yu.pl.app.challeng.notemark.core.api.data

import com.yu.pl.app.challeng.notemark.core.api.domain.NoteApiPayloadCreator
import com.yu.pl.app.challeng.notemark.core.model.Note
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RemoteNoteApiPayloadCreator: NoteApiPayloadCreator {
    override fun createPayLoad(note: Note): String {
        return Json.encodeToString(note.toRemoteNoteMarkApiDto())
    }

}