package com.yu.pl.app.challeng.notemark.core.jobs.domain

import com.yu.pl.app.challeng.notemark.core.model.SyncInterval

interface NoteDataSyncJobExecutor {
    suspend fun executeSync(isReflectRemoteDataInLocal:Boolean = true):Result<Unit>
    suspend fun changeSyncInterval(syncInterval: SyncInterval)
    fun stopPeriodicJob()
}


