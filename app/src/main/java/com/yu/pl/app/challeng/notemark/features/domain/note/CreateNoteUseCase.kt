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
import java.util.UUID
import java.util.concurrent.CancellationException

class CreateNoteUseCase(
    private val noteMarkDao: NoteMarkDao,
    private val noteQueueDao: NoteSyncQueueDao,
    private val preferenceRepository: PreferenceRepository,
) {

    private val newNoteTitle = "New Title"

    @Transaction
    suspend operator fun invoke(): Result<Note> {
        val userId =
            preferenceRepository.getUserId() ?: return Result.failure(IllegalStateException())

        val currentTime = OffsetDateTime.now()
        val newNote = Note(
            id = UUID.randomUUID(),
            title = newNoteTitle,
            content = "",
            createdAt = currentTime,
            lastEditedAt = currentTime
        )
        return try {
            noteMarkDao.insertNoteMark(newNote.toNoteMarkEntity())
            noteQueueDao.insertCreateQueue(
                newNote.toNoteSyncQueueEntity(
                    userId,
                    QueueType.Created,
                )
            )
            Result.success(newNote)
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            Result.failure(e)
        }
    }
}