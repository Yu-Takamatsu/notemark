package com.yu.pl.app.challeng.notemark.features.domain.note

import androidx.room.Transaction
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDao
import com.yu.pl.app.challeng.notemark.core.database.sync.NoteSyncQueueDao
import com.yu.pl.app.challeng.notemark.core.database.sync.QueueType
import com.yu.pl.app.challeng.notemark.core.database.sync.toNoteSyncQueueEntity
import com.yu.pl.app.challeng.notemark.core.database.toNoteMarkEntity
import com.yu.pl.app.challeng.notemark.core.model.Note
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import java.time.OffsetDateTime
import java.util.concurrent.CancellationException

class UpdateNoteUseCase(
    private val noteMarkDao: NoteMarkDao,
    private val noteQueueDao: NoteSyncQueueDao,
    private val preferenceRepository: PreferenceRepository
) {
    @Transaction
    suspend operator fun invoke(originalNote: Note, title:String, content:String): Result<Note> {

        val userId = preferenceRepository.getUserId()?:return Result.failure(IllegalStateException())

        val updateNote = originalNote.copy(
            title = title,
            content = content,
            lastEditedAt = OffsetDateTime.now()
        )

        return try {
            noteMarkDao.updateNoteMark(updateNote.toNoteMarkEntity())
            noteQueueDao.insertUpdatedQueue(
                updateNote.toNoteSyncQueueEntity(
                    userId,
                    QueueType.Updated
                )
            )
            Result.success(updateNote)
        }catch (e: Exception){
            if(e is CancellationException){
                throw e
            }
            Result.failure(e)
        }
    }
}