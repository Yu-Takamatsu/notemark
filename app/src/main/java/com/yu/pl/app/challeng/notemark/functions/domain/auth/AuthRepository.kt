package com.yu.pl.app.challeng.notemark.functions.domain.auth


interface AuthRepository {
    suspend fun login(email:String, password:String): Result<Unit>
    suspend fun logout(token:String): Result<Unit>
}