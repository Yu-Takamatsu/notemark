package com.yu.pl.app.challeng.notemark.core.api.domain

import com.yu.pl.app.challeng.notemark.core.model.Note

interface NoteApi {
    suspend fun postNote(note: Note): Result<Unit>
    suspend fun putNote(note: Note): Result<Unit>
    suspend fun deleteNote(note: Note): Result<Unit>
    suspend fun getNotes(page:Int, size:Int): Result<List<Note>>
    suspend fun getAllNotes(): Result<List<Note>>
}


sealed class NoteApiError: Throwable(){
    data object BadRequest: NoteApiError() {
        private fun readResolve(): Any = BadRequest
    }

    data object Unauthorized: NoteApiError() {
        private fun readResolve(): Any = Unauthorized
    }

    data object MethodNotAllowed: NoteApiError() {
        private fun readResolve(): Any = MethodNotAllowed
    }

    data object Conflict: NoteApiError() {
        private fun readResolve(): Any = Conflict
    }

    data object ManyRequest: NoteApiError() {
        private fun readResolve(): Any = ManyRequest
    }

    data object ServerSide: NoteApiError() {
        private fun readResolve(): Any = ServerSide
    }

    data object Unknown: NoteApiError() {
        private fun readResolve(): Any = Unknown
    }
}