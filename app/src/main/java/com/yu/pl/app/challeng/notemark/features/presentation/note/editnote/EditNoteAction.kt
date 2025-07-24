package com.yu.pl.app.challeng.notemark.features.presentation.note.editnote

import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode

sealed interface EditNoteAction{
    data class OnChangeTitle(val title: String): EditNoteAction
    data class OnChangeContent(val content: String): EditNoteAction
    data class OnChangeEditMode(val editMode: NoteEditMode): EditNoteAction
    data object OnNavigateBack: EditNoteAction
}