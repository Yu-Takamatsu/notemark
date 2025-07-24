package com.yu.pl.app.challeng.notemark.features.domain.note.settings

import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor

class SyncLogoutUseCase(
    private val syncJobExecutor: NoteDataSyncJobExecutor,
    private val unsyncLogoutUseCase: UnsyncLogoutUseCase,
) {

    suspend operator fun invoke(): Result<Unit> {
        syncJobExecutor.executeSync(isReflectRemoteDataInLocal = false).onFailure { failure ->
            return Result.failure(failure)
        }

        val result = unsyncLogoutUseCase()

        return result
    }
}