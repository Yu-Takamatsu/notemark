package com.yu.pl.app.challeng.notemark.functions.data.registration

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val email: String,
    val password: String,
    val username: String
)