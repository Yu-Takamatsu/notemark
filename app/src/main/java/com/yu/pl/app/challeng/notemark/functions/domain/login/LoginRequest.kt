package com.yu.pl.app.challeng.notemark.functions.domain.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)