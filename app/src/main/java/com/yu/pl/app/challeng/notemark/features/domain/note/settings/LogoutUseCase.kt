package com.yu.pl.app.challeng.notemark.features.domain.note.settings

import com.yu.pl.app.challeng.notemark.core.database.sync.NoteSyncQueueDao
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository

class LogoutUseCase(
    private val noteQueueDao: NoteSyncQueueDao,
    private val preferenceRepository: PreferenceRepository,
    private val unsyncLogoutUseCase: UnsyncLogoutUseCase,
) {

    suspend operator fun invoke(): LogoutUseCaseResult {
        val userId = preferenceRepository.getUserId() ?: return LogoutUseCaseResult.Error

        if (noteQueueDao.getAllQueueByUserId(userId).isNotEmpty()) {
            return LogoutUseCaseResult.RemainSyncQueue
        }

        unsyncLogoutUseCase().onFailure { failure ->
            return LogoutUseCaseResult.Error
        }

        return LogoutUseCaseResult.Success
    }
}

sealed interface LogoutUseCaseResult {
    data object Success : LogoutUseCaseResult
    data object RemainSyncQueue : LogoutUseCaseResult
    data object Error : LogoutUseCaseResult
}