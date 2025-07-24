package com.yu.pl.app.challeng.notemark.core.api.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
)


@Serializable
data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val username: String
)

@Serializable
data class RegistrationRequestDto(
    val email: String,
    val password: String,
    val username: String
)

@Serializable
data class LogoutRequestDto(
    val refreshToken:String
)