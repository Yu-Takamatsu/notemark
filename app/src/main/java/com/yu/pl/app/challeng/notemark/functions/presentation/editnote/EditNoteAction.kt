package com.yu.pl.app.challeng.notemark.functions.presentation.editnote

import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteEditMode

sealed interface EditNoteAction{
    data object OnDiscard: EditNoteAction
    data object OnCancelDiscard: EditNoteAction
    data object OnSaveNote: EditNoteAction
    data class OnChangeTitle(val title: String): EditNoteAction
    data class OnChangeContent(val content: String): EditNoteAction
    data class OnChangeEditMode(val editMode: NoteEditMode): EditNoteAction
    data object OnNavigateBack: EditNoteAction
}