package com.yu.pl.app.challeng.notemark.functions.domain.registration

import kotlinx.serialization.Serializable


@Serializable
data class RegistrationRequest(
    val email: String,
    val password: String,
    val username: String
)