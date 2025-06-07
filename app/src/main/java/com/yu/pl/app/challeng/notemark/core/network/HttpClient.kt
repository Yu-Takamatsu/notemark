package com.yu.pl.app.challeng.notemark.core.network

import android.content.Context
import com.yu.pl.app.challeng.notemark.BuildConfig
import com.yu.pl.app.challeng.notemark.core.data.AuthTokenDataStore
import com.yu.pl.app.challeng.notemark.core.domain.model.AuthorizedResponse
import com.yu.pl.app.challeng.notemark.core.domain.model.TokenRefreshRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(context:Context): HttpClient = HttpClient(CIO) {

    val authDataStore = AuthTokenDataStore(context)

    install(Logging){
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }
    defaultRequest {
        header("X-User-Email", BuildConfig.MAIL)
    }

    install(ContentNegotiation){
        json(
            json = Json{
                this.ignoreUnknownKeys = true
            }
        )
    }

    install(Auth) {
        bearer {
            loadTokens {
                val tokenPair = authDataStore.getToken()
                BearerTokens(
                    accessToken = tokenPair?.accessToken ?: "",
                    refreshToken = tokenPair?.refreshToken ?: ""
                )
            }
            refreshTokens {
                // 1. Retrieve current pair of tokens
                val tokenPair = authDataStore.getToken()
                // 2. Use refresh token to get new access token
                val response = client.post(
                    urlString = "https://notemark.pl-coding.com/api/auth/refresh",
                ) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        TokenRefreshRequest(
                            refreshToken = tokenPair?.refreshToken ?: ""
                        )
                    )
                    markAsRefreshTokenRequest()
                // Add this, if you want to test your refresh mechanism.
                // This makes tokens valid for only 30s.
                    header("Debug", true)
                }
                if (response.status == HttpStatusCode.OK) {
                // 3. Update session storage with new pair of tokens
                // (requires adjustment to your local storage)
                    val authToken = Json.decodeFromString<AuthorizedResponse>(response.body())
                    authDataStore.setToken(
                        accessToken = authToken.accessToken,
                        refreshToken = authToken.refreshToken
                    )
                    BearerTokens(
                        accessToken = authToken.accessToken,
                        refreshToken = authToken.refreshToken
                    )
                } else {
                    BearerTokens(
                        accessToken = "",
                        refreshToken = ""
                    )
                }
            }
        }
    }
}