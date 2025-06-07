package com.yu.pl.app.challeng.notemark.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yu.pl.app.challeng.notemark.core.domain.AuthTokenRepository
import com.yu.pl.app.challeng.notemark.core.domain.model.AuthToken
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class AuthTokenDataStore(private val context: Context) : AuthTokenRepository {

    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")

    override suspend fun setToken(accessToken: String, refreshToken: String) {
        context.authDataStore.edit { preference ->
            preference[accessTokenKey] = accessToken
            preference[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun getToken(): AuthToken? {
        return context.authDataStore.data.map { preference ->
            val accessToken = preference[accessTokenKey]
            val refreshToken = preference[refreshTokenKey]

            if (accessToken.isNullOrBlank() || refreshToken.isNullOrBlank()) return@map null
            AuthToken(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }.firstOrNull()
    }
}