package com.yu.pl.app.challeng.notemark.functions.presentation.editnote

import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi

data class EditNoteState(
    val originalNote: NoteMarkUi,
    val isLoading: Boolean,
    val title: String,
    val content: String,
    val isEdit: Boolean,
    val isShowConfirmDiscard: Boolean,
    val editMode: NoteEditMode = NoteEditMode.View
)

