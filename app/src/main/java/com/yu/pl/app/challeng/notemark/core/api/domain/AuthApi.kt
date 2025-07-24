package com.yu.pl.app.challeng.notemark.core.api.domain

import com.yu.pl.app.challeng.notemark.core.model.AuthInfo

interface AuthApi {
    suspend fun login(email:String, password:String): Result<AuthInfo>
    suspend fun logout(token:String): Result<Unit>
    suspend fun registerAccount(email: String, password: String, username: String): Result<Unit>
}


sealed class AuthApiError: Throwable(){
    data object BadRequest: AuthApiError() {
        private fun readResolve(): Any = BadRequest
    }

    data object Unauthorized: AuthApiError() {
        private fun readResolve(): Any = Unauthorized
    }

    data object MethodNotAllowed: AuthApiError() {
        private fun readResolve(): Any = MethodNotAllowed
    }

    data object Conflict: AuthApiError() {
        private fun readResolve(): Any = Conflict
    }

    data object ManyRequest: AuthApiError() {
        private fun readResolve(): Any = ManyRequest
    }

    data object ServerSide: AuthApiError() {
        private fun readResolve(): Any = ServerSide
    }

    data object Unknown: AuthApiError() {
        private fun readResolve(): Any = Unknown
    }
}