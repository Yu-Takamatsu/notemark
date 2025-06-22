package com.yu.pl.app.challeng.notemark.functions.presentation.editnote

sealed interface EditNoteAction{
    data object OnDiscard: EditNoteAction
    data object OnCancelDiscard: EditNoteAction
    data object OnSaveNote: EditNoteAction
    data class OnChangeTitle(val title: String): EditNoteAction
    data class OnChangeContent(val content: String): EditNoteAction

}