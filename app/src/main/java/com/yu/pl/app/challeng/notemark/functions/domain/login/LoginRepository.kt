package com.yu.pl.app.challeng.notemark.functions.domain.login


interface LoginRepository {
    suspend fun login(email:String, password:String): Result<Unit>
}