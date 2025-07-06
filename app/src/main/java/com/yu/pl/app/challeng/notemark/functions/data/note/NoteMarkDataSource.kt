package com.yu.pl.app.challeng.notemark.functions.data.note

import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDao
import com.yu.pl.app.challeng.notemark.core.database.SyncStatus
import com.yu.pl.app.challeng.notemark.core.database.toNoteMark
import com.yu.pl.app.challeng.notemark.core.database.toNoteMarkEntity
import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMark
import com.yu.pl.app.challeng.notemark.functions.domain.note.NoteMarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteMarkDataSource(
    private val noteMarkDao: NoteMarkDao,
    private val remoteApi: NoteMarkApi,
) : NoteMarkRepository {
    override suspend fun createNote(noteMark: NoteMark): Result<Unit> {
        val result = noteMarkDao.insertNoteMark(noteMark.toNoteMarkEntity())

        if (result != -1L) {
            remoteApi.createNote(noteMark)
            return Result.success(Unit)
        }
        return Result.failure(Throwable("fail to insert note into local DB"))
    }

    override suspend fun updateNote(noteMark: NoteMark): Result<Unit> {
        val entity = noteMark.toNoteMarkEntity()
        val result = noteMarkDao.updateNoteMark(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            lastUpdate = entity.lastEditedAt
        )
        if (result >= 1) {
            remoteApi.updateNote(noteMark)
            return Result.success(Unit)
        }
        return Result.failure(Throwable("fail to update Local DB"))
    }

    override suspend fun deleteNote(noteMark: NoteMark): Result<Unit> {

        val entity = noteMark.toNoteMarkEntity()

        val result = noteMarkDao.updateNoteMark(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            lastUpdate = entity.lastEditedAt,
            isDelete = true
        )

        if (result >= 1) {
            remoteApi.deleteNote(noteMark)
            return Result.success(Unit)
        }
        return Result.failure(Throwable("fail to update Local DB"))
    }

    override fun getNotes(): Flow<List<NoteMark>> {
        return noteMarkDao.getAllNoteMark().map { list ->
            list.map { entity ->
                entity.toNoteMark()
            }
        }
    }

    override suspend fun deleteLocalNotes(): Result<Unit> {
        noteMarkDao.deleteAllNoteMark()
        return Result.success(Unit)
    }

    override suspend fun loadNotesFromRemote(): Result<Unit> {
        val notesResult = remoteApi.getNotes(-1, 0)
        if (notesResult.isSuccess) {
            notesResult.getOrNull()?.notes?.map { note ->
                noteMarkDao.insertNoteMark(
                    note.toNoteMarkEntity().copy(
                        syncStatus = SyncStatus.SYNCED
                    )
                )
            }
            return Result.success(Unit)
        } else {
            return Result.failure(Exception())
        }
    }
}