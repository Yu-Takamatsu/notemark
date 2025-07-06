package com.yu.pl.app.challeng.notemark.functions.domain.note

import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMark
import kotlinx.coroutines.flow.Flow

interface NoteMarkRepository {
    suspend fun createNote(noteMark: NoteMark): Result<Unit>
    suspend fun updateNote(noteMark: NoteMark): Result<Unit>
    suspend fun deleteNote(noteMark: NoteMark): Result<Unit>
    fun getNotes(): Flow<List<NoteMark>>
    suspend fun deleteLocalNotes(): Result<Unit>
    suspend fun loadNotesFromRemote(): Result<Unit>
}