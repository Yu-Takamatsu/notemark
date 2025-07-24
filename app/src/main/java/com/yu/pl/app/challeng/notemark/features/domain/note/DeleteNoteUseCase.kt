package com.yu.pl.app.challeng.notemark.features.domain.note

import androidx.room.Transaction
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDao
import com.yu.pl.app.challeng.notemark.core.database.sync.NoteSyncQueueDao
import com.yu.pl.app.challeng.notemark.core.database.sync.QueueType
import com.yu.pl.app.challeng.notemark.core.database.sync.toNoteSyncQueueEntity
import com.yu.pl.app.challeng.notemark.core.database.toNoteMarkEntity
import com.yu.pl.app.challeng.notemark.core.model.Note
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import java.util.concurrent.CancellationException

class DeleteNoteUseCase(
    private val noteMarkDao: NoteMarkDao,
    private val noteQueueDao: NoteSyncQueueDao,
    private val preferenceRepository: PreferenceRepository
)  {
    @Transaction
    suspend operator fun invoke(note: Note): Result<Unit> {

        val userId = preferenceRepository.getUserId()?:return Result.failure(IllegalStateException())

        return try {
            noteMarkDao.deleteNoteMark(note.toNoteMarkEntity())
            noteQueueDao.insertDeletedQueue(
                note.toNoteSyncQueueEntity(
                    userId,
                    QueueType.Deleted,
                )
            )
            Result.success(Unit)
        }catch (e: Exception){
            if(e is CancellationException){
                throw e
            }
            Result.failure(e)
        }
    }
}