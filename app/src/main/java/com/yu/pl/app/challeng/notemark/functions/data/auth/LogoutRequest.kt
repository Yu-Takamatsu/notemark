package com.yu.pl.app.challeng.notemark.functions.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    val refreshToken:String
)