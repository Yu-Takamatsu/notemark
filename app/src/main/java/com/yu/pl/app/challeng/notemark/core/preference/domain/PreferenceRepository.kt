package com.yu.pl.app.challeng.notemark.core.preference.domain

import com.yu.pl.app.challeng.notemark.core.model.AuthToken
import com.yu.pl.app.challeng.notemark.core.model.AuthInfo
import com.yu.pl.app.challeng.notemark.core.model.SyncInterval
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

interface PreferenceRepository {
    suspend fun setToken(accessToken:String, refreshToken:String)
    suspend fun setAuthInfo(authInfo: AuthInfo)
    suspend fun getToken(): AuthToken?
    suspend fun setUserId(userId:String)
    suspend fun getUserName(): String?
    suspend fun getUserId(): String?
    suspend fun deleteUserPreference()
    suspend fun setSyncTimestamp(timestamp: OffsetDateTime)
    val getSyncTimestamp:Flow<OffsetDateTime?>
    suspend fun setSyncInterval(interval: SyncInterval)
    val getSyncInterval:Flow<SyncInterval>
}