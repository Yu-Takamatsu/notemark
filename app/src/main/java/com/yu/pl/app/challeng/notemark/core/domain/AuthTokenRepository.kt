package com.yu.pl.app.challeng.notemark.core.domain

import com.yu.pl.app.challeng.notemark.core.domain.model.AuthToken

interface AuthTokenRepository {
    suspend fun setToken(accessToken:String, refreshToken:String)
    suspend fun getToken(): AuthToken?
}