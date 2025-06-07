package com.yu.pl.app.challeng.notemark.functions.domain.registration

interface RegistrationRepository {
    suspend fun registerAccount(requestData: RegistrationRequest): Result<Unit>
}