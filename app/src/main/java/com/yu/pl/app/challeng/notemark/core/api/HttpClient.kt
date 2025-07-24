package com.yu.pl.app.challeng.notemark.core.api

import android.content.Context
import com.yu.pl.app.challeng.notemark.BuildConfig
import com.yu.pl.app.challeng.notemark.core.preference.data.PreferenceDataStore
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun createHttpClient(context:Context): HttpClient = HttpClient(CIO) {

    val authDataStore = PreferenceDataStore(context)

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

                val tokenPair = authDataStore.getToken()
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
                    header("Debug", true)
                }
                if (response.status == HttpStatusCode.OK) {
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


@Serializable
data class AuthorizedResponse(
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class TokenRefreshRequest(
    val refreshToken: String
)