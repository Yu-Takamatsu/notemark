package com.yu.pl.app.challeng.notemark.functions.data.registration

import com.yu.pl.app.challeng.notemark.functions.domain.registration.RegistrationRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class RegistrationApi(
    private val httpClient: HttpClient
): RegistrationRepository {
    override suspend fun registerAccount(email: String, password: String, username: String): Result<Unit> {

        val result = httpClient.post {
            url("https://notemark.pl-coding.com/api/auth/register")
            setBody(RegistrationRequest(
                email = email,
                password = password,
                username = username
            ))
            contentType(ContentType.Application.Json)
        }
        try {
            if (result.status == HttpStatusCode.OK) {
                return Result.success(Unit)
            }
            return Result.failure(IllegalStateException())
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

}