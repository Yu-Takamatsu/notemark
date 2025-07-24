package com.yu.pl.app.challeng.notemark.features.domain.note.settings

import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor
import com.yu.pl.app.challeng.notemark.core.model.SyncInterval

class ChangeSyncIntervalUseCase (
    private val syncJobExecutor: NoteDataSyncJobExecutor
){
    suspend operator fun invoke(interval: SyncInterval){
        syncJobExecutor.changeSyncInterval(interval)
    }
}