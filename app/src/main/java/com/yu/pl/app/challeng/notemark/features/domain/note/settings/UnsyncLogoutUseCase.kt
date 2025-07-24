package com.yu.pl.app.challeng.notemark.features.domain.note.settings

import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApi
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDao
import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository

class UnsyncLogoutUseCase(
    private val preferenceRepository: PreferenceRepository,
    private val authApi: AuthApi,
    private val noteMarkDao: NoteMarkDao,
    private val noteDataSyncJobExecutor: NoteDataSyncJobExecutor,
) {

    suspend operator fun invoke(): Result<Unit> {

        val authToken = preferenceRepository.getToken() ?: return Result.failure(
            IllegalStateException()
        )

        authApi.logout(authToken.refreshToken).onFailure { failure ->
            return Result.failure(failure)
        }

        noteMarkDao.deleteAllNoteMark()
        preferenceRepository.deleteUserPreference()
        noteDataSyncJobExecutor.stopPeriodicJob()
        return Result.success(Unit)

    }
}