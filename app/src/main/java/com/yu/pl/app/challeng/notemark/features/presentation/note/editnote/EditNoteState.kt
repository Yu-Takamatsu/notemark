package com.yu.pl.app.challeng.notemark.features.presentation.note.editnote

import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi

data class EditNoteState(
    val originalNote: NoteMarkUi,
    val isLoading: Boolean,
    val title: String,
    val content: String,
    val editMode: NoteEditMode = NoteEditMode.View
)

