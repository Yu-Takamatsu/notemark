package com.yu.pl.app.challeng.notemark.functions.data.login

import com.yu.pl.app.challeng.notemark.core.domain.AuthTokenRepository
import com.yu.pl.app.challeng.notemark.functions.domain.login.LoginRepository
import com.yu.pl.app.challeng.notemark.functions.domain.login.LoginRequest
import com.yu.pl.app.challeng.notemark.functions.domain.login.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class LoginApi(
    private val httpClient: HttpClient,
    private val authTokenRepository: AuthTokenRepository,
) : LoginRepository {
    override suspend fun login(requestData: LoginRequest): Result<Unit> {

        try {
            val response = httpClient.post {
                url("https://notemark.pl-coding.com/api/auth/login")
                setBody(requestData)
                contentType(ContentType.Application.Json)
            }

            if(response.status == HttpStatusCode.OK){
                val body = Json.decodeFromString<LoginResponse>(response.body())
                authTokenRepository.setToken(body.accessToken, body.refreshToken)
                return Result.success(Unit)
            }
            return Result.failure(IllegalStateException())
        }catch (e: Exception){
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}