package com.yu.pl.app.challeng.notemark.functions.domain.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)