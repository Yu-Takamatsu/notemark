package com.yu.pl.app.challeng.notemark.features.domain.auth

import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApi
import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApiError
import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import io.ktor.util.decodeBase64String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class LoginUseCase(
    private val authApi: AuthApi,
    private val noteDataSyncJobExecutor: NoteDataSyncJobExecutor,
    private val preferenceRepository: PreferenceRepository,
) {

    suspend operator fun invoke(email: String, password: String): Result<LoginResult> {

        try {
            val apiResult = authApi.login(email, password)

            val authInfo = try {
                apiResult.getOrThrow()
            } catch (e: Throwable) {
                return when (e) {
                    is AuthApiError.Unauthorized -> {
                        Result.success(LoginResult.UnAuthorized)
                    }
                    is AuthApiError.ServerSide -> {
                        Result.success(LoginResult.ServeSideError)
                    }
                    else -> {
                        Result.success(LoginResult.UnknownError)
                    }
                }
            }
            preferenceRepository.setAuthInfo(authInfo)
            val userId = createUserId(authInfo.accessToken)
            preferenceRepository.setUserId(userId)

            syncData().onFailure {
                return Result.success(LoginResult.DataSyncError)
            }

            return Result.success(LoginResult.Success)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    private suspend fun syncData(): Result<Unit> {
        return noteDataSyncJobExecutor.executeSync()
    }


    private fun createUserId(accessToken: String): String {
        return Json.decodeFromString<AccessTokenDecode>(accessToken.split(".")[1].decodeBase64String()).sub
    }

    @Serializable
    private data class AccessTokenDecode(
        val sub: String,
        val type: String,
        val iat: Long,
        val exp: Long,
    )
}

sealed interface LoginResult {
    data object Success : LoginResult
    data object UnAuthorized : LoginResult
    data object ServeSideError : LoginResult
    data object UnknownError : LoginResult
    data object DataSyncError : LoginResult
}
