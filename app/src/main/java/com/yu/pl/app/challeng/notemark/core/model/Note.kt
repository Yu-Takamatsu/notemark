package com.yu.pl.app.challeng.notemark.core.model

import java.time.OffsetDateTime
import java.util.UUID

/**
 * Data class representing a note mark.
 *
 * @param id Unique identifier for the note, formatted as a UUID string.
 * @param title The title of the note.
 * @param content The main content of the note.
 * @param createdAt The timestamp when the note was created, in ISO 8601 format.
 * @param lastEditedAt The timestamp when the note was last edited, in ISO 8601 format.
 */
data class Note(
    val id: UUID,
    val title: String,
    val content: String,
    val createdAt: OffsetDateTime,
    val lastEditedAt: OffsetDateTime
)