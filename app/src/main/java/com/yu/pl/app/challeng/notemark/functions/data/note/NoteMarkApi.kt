package com.yu.pl.app.challeng.notemark.functions.data.note

import androidx.room.Transaction
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDao
import com.yu.pl.app.challeng.notemark.core.database.SyncStatus
import com.yu.pl.app.challeng.notemark.core.database.toNoteMarkEntity
import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMark
import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMarkList
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class NoteMarkApi(
    private val httpClient: HttpClient,
    private val scope: CoroutineScope,
    private val noteDao: NoteMarkDao
) {
    val apiUrl = "https://notemark.pl-coding.com/api/notes"

    fun createNote(noteMark: NoteMark): Result<Unit> {
        scope.launch {
            postNote(noteMark)
        }
        return Result.success(Unit)
    }

    @Transaction
    suspend fun postNote(noteMark: NoteMark){
        try {
            noteDao.updateNoteMark(
                noteMark.toNoteMarkEntity().copy(
                    syncStatus = SyncStatus.SYNCED
                )
            )
            val response = httpClient.post {
                url(apiUrl)
                setBody(noteMark.toNoteMarkDto())
                contentType(ContentType.Application.Json)
            }

            if (response.status != HttpStatusCode.OK) {
                throw Exception()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }


    fun updateNote(noteMark: NoteMark): Result<Unit> {
        scope.launch {
            putNote(noteMark)
        }
        return Result.success(Unit)
    }

    suspend fun putNote(noteMark: NoteMark){
        try {
            noteDao.updateNoteMark(
                noteMark.toNoteMarkEntity().copy(
                    syncStatus = SyncStatus.SYNCED
                )
            )
            val response = httpClient.put {
                url(apiUrl)
                setBody(noteMark.toNoteMarkDto())
                contentType(ContentType.Application.Json)
            }

            if (response.status != HttpStatusCode.OK) {
                throw Exception()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }


    fun deleteNote(noteMark: NoteMark): Result<Unit> {
        scope.launch {
            deleteTransaction(noteMark)
        }
        return Result.success(Unit)
    }

    @Transaction
    suspend fun deleteTransaction(noteMark: NoteMark){
        try {
            noteDao.deleteNoteMark(
                noteMark.toNoteMarkEntity().copy(
                    syncStatus = SyncStatus.SYNCED,
                    isDelete = true
                )
            )
            val response = httpClient.delete {
                url("${apiUrl}/${noteMark.id}")
                setBody(noteMark.toNoteMarkDto())
                contentType(ContentType.Application.Json)
            }

            if (response.status != HttpStatusCode.OK) {
                throw Exception()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend fun getNotes(
        page: Int,
        size: Int,
    ): Result<NoteMarkList> {
        try {
            val response = httpClient.get {
                url(apiUrl)
                parameter("page", page)
                parameter("size", size)
                contentType(ContentType.Application.Json)
            }

            if (response.status == HttpStatusCode.OK) {
                val body = Json.decodeFromString<NoteMarkResponse>(response.body())
                return Result.success(body.toNoteMarList())
            }
            return Result.failure(IllegalStateException())
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}