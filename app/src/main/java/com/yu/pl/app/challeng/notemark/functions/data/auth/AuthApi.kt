package com.yu.pl.app.challeng.notemark.functions.data.auth

import com.yu.pl.app.challeng.notemark.core.domain.AuthTokenRepository
import com.yu.pl.app.challeng.notemark.functions.domain.auth.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class AuthApi(
    private val httpClient: HttpClient,
    private val authTokenRepository: AuthTokenRepository,
) : AuthRepository {
    private val apiHost = "https://notemark.pl-coding.com"

    override suspend fun login(email:String, password:String): Result<Unit> {

        try {
            val response = httpClient.post {
                url("$apiHost/api/auth/login")
                setBody(LoginRequest(
                    email = email,
                    password = password
                ))
                contentType(ContentType.Application.Json)
            }

            if(response.status == HttpStatusCode.OK){
                val body = Json.decodeFromString<LoginResponse>(response.body())
                authTokenRepository.setToken(body.accessToken, body.refreshToken)
                authTokenRepository.setUserName(body.username)
                return Result.success(Unit)
            }
            return Result.failure(IllegalStateException())
        }catch (e: Exception){
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    override suspend fun logout(token: String): Result<Unit> {
        try {
            val response = httpClient.post {
                url("$apiHost/api/auth/logout")
                setBody(LogoutRequest(
                    refreshToken = token
                ))
                contentType(ContentType.Application.Json)
            }

            if(response.status == HttpStatusCode.OK){
                return Result.success(Unit)
            }
            return Result.failure(IllegalStateException())
        }catch (e: Exception){
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}