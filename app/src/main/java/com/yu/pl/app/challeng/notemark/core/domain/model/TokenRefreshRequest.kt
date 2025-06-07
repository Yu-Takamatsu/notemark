package com.yu.pl.app.challeng.notemark.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    val refreshToken: String
)