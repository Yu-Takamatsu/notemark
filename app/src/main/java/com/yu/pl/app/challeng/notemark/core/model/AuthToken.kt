package com.yu.pl.app.challeng.notemark.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val accessToken: String,
    val refreshToken:String
)