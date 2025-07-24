package com.yu.pl.app.challeng.notemark.core.api.data

import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApi
import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApiError
import com.yu.pl.app.challeng.notemark.core.model.AuthInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class RemoteAuthApi(private val httpClient: HttpClient) : AuthApi {
    private val apiHost = "https://notemark.pl-coding.com"

    override suspend fun login(email: String, password: String): Result<AuthInfo> {

        try {
            val result = httpClient.post {
                url("$apiHost/api/auth/login")
                setBody(
                    LoginRequestDto(
                        email = email,
                        password = password
                    )
                )
                contentType(ContentType.Application.Json)
            }

            if (result.status == HttpStatusCode.OK) {
                val body = Json.decodeFromString<LoginResponseDto>(result.body())

                return Result.success(
                    AuthInfo(
                        accessToken = body.accessToken,
                        refreshToken = body.refreshToken,
                        userName = body.username
                    )
                )
            }

            return Result.failure(mapApiError(result.status))
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(AuthApiError.Unknown)
        }
    }

    override suspend fun logout(token: String): Result<Unit> {
        try {
            val result = httpClient.post {
                url("$apiHost/api/auth/logout")
                setBody(
                    LogoutRequestDto(
                        refreshToken = token
                    )
                )
                contentType(ContentType.Application.Json)
            }

            if (result.status == HttpStatusCode.OK) {
                return Result.success(Unit)
            }
            return Result.failure(mapApiError(result.status))
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(AuthApiError.Unknown)
        }
    }

    override suspend fun registerAccount(
        email: String,
        password: String,
        username: String,
    ): Result<Unit> {
        val result = httpClient.post {
            url("https://notemark.pl-coding.com/api/auth/register")
            setBody(
                RegistrationRequestDto(
                    email = email,
                    password = password,
                    username = username
                )
            )
            contentType(ContentType.Application.Json)
        }
        try {
            if (result.status == HttpStatusCode.OK) {
                return Result.success(Unit)
            }
            return Result.failure(mapApiError(result.status))
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(AuthApiError.Unknown)
        }
    }

    private fun mapApiError(code: HttpStatusCode): AuthApiError {

        return when (code.value) {
            400 -> AuthApiError.BadRequest
            401 -> AuthApiError.Unauthorized
            405 -> AuthApiError.MethodNotAllowed
            409 -> AuthApiError.Conflict
            429 -> AuthApiError.ManyRequest
            in 500..599 -> AuthApiError.ServerSide
            else -> AuthApiError.Unknown
        }
    }


}