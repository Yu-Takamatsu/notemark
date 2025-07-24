package com.yu.pl.app.challeng.notemark.core.jobs

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor

class NoteDataSyncWorker(
    appContext: Context,
    params: WorkerParameters,
    private val jobExecutor: NoteDataSyncJobExecutor,
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val result = jobExecutor.executeSync()

        if(result.isFailure){
            return Result.failure()
        }

        return Result.success()
    }

    companion object{
        val PERIOID_WORK_NAME = "PeriodicSyncNoteDataWork"
    }
}