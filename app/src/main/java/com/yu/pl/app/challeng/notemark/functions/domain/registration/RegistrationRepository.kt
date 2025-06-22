package com.yu.pl.app.challeng.notemark.functions.domain.registration

interface RegistrationRepository {
    suspend fun registerAccount(email: String, password: String, username: String): Result<Unit>
}