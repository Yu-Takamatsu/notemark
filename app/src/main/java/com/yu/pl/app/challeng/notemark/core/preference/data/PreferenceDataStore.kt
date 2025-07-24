package com.yu.pl.app.challeng.notemark.core.preference.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import com.yu.pl.app.challeng.notemark.core.model.AuthToken
import com.yu.pl.app.challeng.notemark.core.model.AuthInfo
import com.yu.pl.app.challeng.notemark.core.model.SyncInterval
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset


val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = "pref_store")

class PreferenceDataStore(
    private val context: Context,
) : PreferenceRepository {

    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val userNameKey = stringPreferencesKey("user_name")
    private val userIdKey = stringPreferencesKey("user_id")
    private val lastSyncTimeKey = longPreferencesKey("last_sync")
    private val syncIntervalKey = stringPreferencesKey("sync_interval")

    override suspend fun setToken(accessToken: String, refreshToken: String) {
        context.preferenceDataStore.edit { preference ->
            preference[accessTokenKey] = accessToken
            preference[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun setAuthInfo(authInfo: AuthInfo) {
        context.preferenceDataStore.edit { preference ->
            preference[accessTokenKey] = authInfo.accessToken
            preference[refreshTokenKey] = authInfo.refreshToken
            preference[userNameKey] = authInfo.userName
        }
    }

    override suspend fun getToken(): AuthToken? {
        return context.preferenceDataStore.data.map { preference ->
            val accessToken = preference[accessTokenKey]
            val refreshToken = preference[refreshTokenKey]

            if (accessToken.isNullOrBlank() || refreshToken.isNullOrBlank()) return@map null
            AuthToken(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }.firstOrNull()
    }

    override suspend fun setUserId(userId: String) {
        context.preferenceDataStore.edit { preference ->
            preference[userIdKey] = userId
        }
    }

    override suspend fun getUserName(): String? {
        return context.preferenceDataStore.data.map { preference ->
            preference[userNameKey]
        }.firstOrNull()
    }

    override suspend fun getUserId(): String? {
        return context.preferenceDataStore.data.map { preference ->
            preference[userIdKey]
        }.firstOrNull()
    }

    override suspend fun deleteUserPreference() {
        context.preferenceDataStore.edit { preference ->
            preference.remove(accessTokenKey)
            preference.remove(refreshTokenKey)
            preference.remove(userNameKey)
            preference.remove(lastSyncTimeKey)
            preference.remove(userIdKey)
            preference.remove(syncIntervalKey)
        }
    }

    override suspend fun setSyncTimestamp(timestamp: OffsetDateTime) {
        context.preferenceDataStore.edit { preference ->
            preference[lastSyncTimeKey] = timestamp.toEpochSecond()
        }
    }

    override val getSyncTimestamp: Flow<OffsetDateTime?> =
        context.preferenceDataStore.data.map { preference ->
            val timestamp = preference[lastSyncTimeKey] ?: return@map null
            OffsetDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.systemDefault())
        }

    override suspend fun setSyncInterval(interval: SyncInterval) {
        context.preferenceDataStore.edit { preference->
            preference[syncIntervalKey] = interval.name
        }
    }

    override val getSyncInterval: Flow<SyncInterval> =
        context.preferenceDataStore.data.map { preference ->
            try {
                val name = preference[syncIntervalKey] ?: return@map SyncInterval.Manual
                SyncInterval.fromNameOrThrow(name)
            }catch (_: Exception){
                SyncInterval.Manual
            }
        }
}