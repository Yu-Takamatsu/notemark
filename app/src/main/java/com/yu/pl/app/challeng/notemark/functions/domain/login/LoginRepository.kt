package com.yu.pl.app.challeng.notemark.functions.domain.login

interface LoginRepository {
    suspend fun login(requestData: LoginRequest): Result<Unit>
}