package com.yu.pl.app.challeng.notemark.functions.presentation.models

import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMark
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
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

fun NoteMark.toNoteMarkUi(): NoteMarkUi {
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
        if (System.currentTimeMillis() - createdAt.toEpochSecond() * 1000 < 5 * 60 * 1000) {
            "Just Now"
        } else {
            createdAt.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
        }

    val editDateText =
        if (System.currentTimeMillis() - lastEditedAt.toEpochSecond() * 1000 < 5 * 60 * 1000) {
            "Just Now"
        } else {
            lastEditedAt.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
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

fun NoteMarkUi.toNoteMark(): NoteMark {
    return NoteMark(
        id = UUID.fromString(this.id),
        title = this.title,
        content = this.content,
        createdAt = unixTimeToOffsetDateTime(this.createAt),
        lastEditedAt = unixTimeToOffsetDateTime(this.lastEditedAt),
    )
}

fun unixTimeToOffsetDateTime(
    unixTimeSeconds: Long,
    zoneId: ZoneId = ZoneId.of("UTC"),
): OffsetDateTime {
    // Unix秒からInstantを作成し、指定されたタイムゾーンIDでOffsetDateTimeに変換
    return OffsetDateTime.ofInstant(Instant.ofEpochSecond(unixTimeSeconds), zoneId)
}