package com.yu.pl.app.challeng.notemark.core.api.data

import com.yu.pl.app.challeng.notemark.core.api.domain.NoteApi
import com.yu.pl.app.challeng.notemark.core.api.domain.NoteApiError
import com.yu.pl.app.challeng.notemark.core.model.Note
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class RemoteNoteApi(private val httpClient: HttpClient) : NoteApi {

    private val apiUrl = "https://notemark.pl-coding.com/api/notes"

    override suspend fun postNote(note: Note): Result<Unit> {
        return try {
            val response = httpClient.post {
                url(apiUrl)
                setBody(note.toRemoteNoteMarkApiDto())
                contentType(ContentType.Application.Json)
            }
            if(response.status == HttpStatusCode.OK) {
                return Result.success(Unit)
            }
            Result.failure(mapApiError(response.status))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(NoteApiError.Unknown)
        }
    }

    override suspend fun putNote(note: Note): Result<Unit> {
        return try {
            val response = httpClient.put {
                url(apiUrl)
                setBody(note.toRemoteNoteMarkApiDto())
                contentType(ContentType.Application.Json)
            }
            if(response.status == HttpStatusCode.OK) {
                return Result.success(Unit)
            }
            Result.failure(mapApiError(response.status))

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(NoteApiError.Unknown)
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit> {
        return try {
            val response = httpClient.delete {
                url("${apiUrl}/${note.id}")
                setBody(note.toRemoteNoteMarkApiDto())
                contentType(ContentType.Application.Json)
            }
            if(response.status == HttpStatusCode.OK) {
                return Result.success(Unit)
            }

            Result.failure(mapApiError(response.status))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(NoteApiError.Unknown)
        }
    }

    override suspend fun getNotes(page:Int,size:Int): Result<List<Note>> {
        return try {
            val response = httpClient.get {
                url(apiUrl)
                parameter("page", page)
                parameter("size", size)
                contentType(ContentType.Application.Json)
            }
            if (response.status == HttpStatusCode.OK) {
                val body = Json.decodeFromString<RemoteNoteMarkListApiDto>(response.body())
                return Result.success(body.toNoteMarList())
            }
            Result.failure(mapApiError(response.status))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(NoteApiError.Unknown)
        }
    }


    override suspend fun getAllNotes(): Result<List<Note>> {
        return getNotes(-1, 0)
    }

    private fun mapApiError(code: HttpStatusCode): NoteApiError {

        return when (code.value) {
            400 -> NoteApiError.BadRequest
            401 -> NoteApiError.Unauthorized
            405 -> NoteApiError.MethodNotAllowed
            409 -> NoteApiError.Conflict
            429 -> NoteApiError.ManyRequest
            in 500..599 -> NoteApiError.ServerSide
            else -> NoteApiError.Unknown
        }
    }
}