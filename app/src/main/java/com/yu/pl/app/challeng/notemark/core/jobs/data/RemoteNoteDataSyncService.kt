package com.yu.pl.app.challeng.notemark.core.jobs.data

import android.content.Context
import androidx.room.Transaction
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.yu.pl.app.challeng.notemark.core.api.domain.NoteApi
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDao
import com.yu.pl.app.challeng.notemark.core.database.sync.NoteSyncQueueDao
import com.yu.pl.app.challeng.notemark.core.database.sync.NoteSyncQueueEntity
import com.yu.pl.app.challeng.notemark.core.database.sync.QueueType
import com.yu.pl.app.challeng.notemark.core.database.toNoteMarkEntity
import com.yu.pl.app.challeng.notemark.core.jobs.NoteDataSyncWorker
import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor
import com.yu.pl.app.challeng.notemark.core.model.SyncInterval
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

class RemoteNoteDataSyncService(
    private val noteApi: NoteApi,
    private val syncQueueDao: NoteSyncQueueDao,
    private val noteDao: NoteMarkDao,
    private val preference: PreferenceRepository,
    private val context: Context,
) : NoteDataSyncJobExecutor {
    override suspend fun executeSync(isReflectRemoteDataInLocal: Boolean): Result<Unit> {
        val userId = preference.getUserId() ?: return Result.failure(IllegalStateException())
        val queueList = syncQueueDao.getAllQueueByUserId(userId)

        queueList.forEach { entity ->
            syncNoteData(entity).onFailure { failure ->
                return Result.failure(failure)
            }
        }
        if (isReflectRemoteDataInLocal) {
            getRemoteNoteData()
            preference.setSyncTimestamp(OffsetDateTime.now())
        }
        return Result.success(Unit)
    }

    private suspend fun getRemoteNoteData(): Result<Unit> {
        val result = noteApi.getAllNotes()

        return try {
            val notes = result.getOrThrow()
            notes.forEach { note ->
                noteDao.insertOrUpdateWithConflictCheck(note.toNoteMarkEntity())
            }
            Result.success(Unit)
        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }


    override suspend fun changeSyncInterval(syncInterval: SyncInterval) {
        preference.setSyncInterval(syncInterval)

        when (syncInterval) {
            SyncInterval.Manual -> {
                val workManager = WorkManager.getInstance(context)
                workManager.cancelUniqueWork(NoteDataSyncWorker.PERIOID_WORK_NAME)
            }

            is SyncInterval.Hour1 -> setPeriodicNoteSyncWork(syncInterval.interval)
            is SyncInterval.Minutes15 -> setPeriodicNoteSyncWork(syncInterval.interval)
            is SyncInterval.Minutes30 -> setPeriodicNoteSyncWork(syncInterval.interval)
        }
    }

    override fun stopPeriodicJob() {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(NoteDataSyncWorker.PERIOID_WORK_NAME)
    }

    private fun setPeriodicNoteSyncWork(duration: Duration) {
        val workRequest = PeriodicWorkRequestBuilder<NoteDataSyncWorker>(
            duration.inWholeMinutes,
            TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            )
            .setInitialDelay(duration.inWholeMinutes, TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = NoteDataSyncWorker.PERIOID_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.UPDATE,
            request = workRequest
        )
    }


    @Transaction
    private suspend fun syncNoteData(entity: NoteSyncQueueEntity): Result<Unit> {

        when (entity.type) {
            QueueType.Created -> noteApi.postNote(entity.note)
            QueueType.Updated -> noteApi.putNote(entity.note)
            QueueType.Deleted -> noteApi.deleteNote(entity.note)
        }.onFailure { failure ->
            return Result.failure(failure)
        }

        syncQueueDao.deleteQueueById(entity.id)

        return Result.success(Unit)
    }
}