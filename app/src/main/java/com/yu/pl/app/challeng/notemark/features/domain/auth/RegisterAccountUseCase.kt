package com.yu.pl.app.challeng.notemark.features.domain.auth

import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApi
import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApiError


class RegisterAccountUseCase(
    private val authApi: AuthApi,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        username: String,
    ): Result<RegisterResult> {

        try {
            authApi.registerAccount(
                email = email,
                password = password,
                username = username
            ).onFailure { failure ->
                when (failure) {
                    is AuthApiError.Unauthorized -> {
                        return Result.success(RegisterResult.InvalidError)
                    }

                    is AuthApiError.ServerSide -> {
                        return Result.success(RegisterResult.ServiceSideError)
                    }

                    is AuthApiError.Conflict,
                    AuthApiError.ManyRequest,
                        -> {
                        return Result.success(RegisterResult.Conflict)
                    }

                    is AuthApiError.BadRequest,
                    AuthApiError.MethodNotAllowed,
                    AuthApiError.Unknown,
                        -> {
                        return Result.success(RegisterResult.UnexpectedError)
                    }

                    else -> {
                        return Result.failure(IllegalStateException())
                    }
                }
            }

            return Result.success(RegisterResult.Success)

        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}

sealed interface RegisterResult {
    data object Success : RegisterResult
    data object InvalidError : RegisterResult
    data object ServiceSideError : RegisterResult
    data object Conflict : RegisterResult
    data object UnexpectedError : RegisterResult
}