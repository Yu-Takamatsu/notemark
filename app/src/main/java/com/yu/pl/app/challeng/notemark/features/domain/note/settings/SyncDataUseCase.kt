package com.yu.pl.app.challeng.notemark.features.domain.note.settings

import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor

class SyncDataUseCase(
    private val syncJobExecutor: NoteDataSyncJobExecutor
){
    suspend operator fun invoke():Result<Unit>{
        return syncJobExecutor.executeSync()
    }
}