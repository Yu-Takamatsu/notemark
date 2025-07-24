package com.yu.pl.app.challeng.notemark.features.presentation.models

import com.yu.pl.app.challeng.notemark.core.model.Note
import com.yu.pl.app.challeng.notemark.core.ui.util.DateFormat
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale
import java.util.UUID

@Serializable
data class NoteMarkUi(
    val id: String,
    val title: String,
    val content: String,
    val createAt: Long,
    val lastEditedAt: Long,
    val createDataSummary: String,
    val createDateText: String,
    val editDateText: String,
)

fun Note.toNoteMarkUi(): NoteMarkUi {
    val currentYear = OffsetDateTime.now().year
    val createDataSummary = if (this.createdAt.year == currentYear) {
        "${this.createdAt.dayOfMonth} ${
            this.createdAt.month.getDisplayName(
                TextStyle.SHORT,
                Locale.ENGLISH
            )
        }"
    } else {
        "${this.createdAt.dayOfMonth} ${
            this.createdAt.month.getDisplayName(
                TextStyle.SHORT,
                Locale.ENGLISH
            )
        } ${this.createdAt.year}"
    }

    val createDateText =
        DateFormat.yearToSecond(Locale.getDefault()).format(Date(createdAt.toEpochSecond() * 1000))

    val editDateText =
        if (System.currentTimeMillis() - lastEditedAt.toEpochSecond() * 1000 < 5 * 60 * 1000) {
            "Just Now"
        } else {
            DateFormat.yearToSecond(Locale.getDefault()).format(Date(createdAt.toEpochSecond() * 1000))
        }

    return NoteMarkUi(
        id = this.id.toString(),
        title = this.title,
        content = this.content,
        createAt = this.createdAt.toEpochSecond(),
        lastEditedAt = this.lastEditedAt.toEpochSecond(),
        createDataSummary = createDataSummary,
        createDateText = createDateText,
        editDateText = editDateText
    )
}

fun NoteMarkUi.toNoteMark(): Note {
    return Note(
        id = UUID.fromString(this.id),
        title = this.title,
        content = this.content,
        createdAt = unixTimeToOffsetDateTime(this.createAt),
        lastEditedAt = unixTimeToOffsetDateTime(this.lastEditedAt),
    )
}

fun unixTimeToOffsetDateTime(
    unixTimeSeconds: Long
): OffsetDateTime {
    return OffsetDateTime.ofInstant(Instant.ofEpochSecond(unixTimeSeconds), ZoneOffset.UTC)
}