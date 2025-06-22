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
    private val remoteApi: NoteMarkApi
): NoteMarkRepository {
    override suspend fun createNote(noteMark: NoteMark): Result<Unit> {
        val result = noteMarkDao.insertNoteMark(noteMark.toNoteMarkEntity())

        if(result != -1L){
            val apiResult = remoteApi.createNote(noteMark)
            if(apiResult.isSuccess){
                noteMarkDao.updateNoteMark(noteMark.toNoteMarkEntity().copy(
                    syncStatus = SyncStatus.SYNCED
                ))
            }
            return Result.success(Unit)
        }
        return Result.failure(Throwable("fail to insert note into local DB"))
    }

    override suspend fun updateNote(noteMark: NoteMark): Result<Unit> {
        val result = noteMarkDao.updateNoteMark(noteMark.toNoteMarkEntity())
        if(result >=1){
            val apiResult = remoteApi.updateNote(noteMark)
            if(apiResult.isSuccess){
                noteMarkDao.updateNoteMark(noteMark.toNoteMarkEntity().copy(
                    syncStatus = SyncStatus.SYNCED
                ))
            }
        }
        return Result.failure(Throwable("fail to update Local DB"))
    }

    override suspend fun deleteNote(noteMark: NoteMark): Result<Unit> {
        val result = noteMarkDao.updateNoteMark(noteMark.toNoteMarkEntity().copy(
            isDelete = true
        ))

        if(result >=1){
            val apiResult = remoteApi.deleteNote(noteMark)
            if(apiResult.isSuccess){
                noteMarkDao.deleteNoteMark(noteMark.toNoteMarkEntity())
            }
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
}