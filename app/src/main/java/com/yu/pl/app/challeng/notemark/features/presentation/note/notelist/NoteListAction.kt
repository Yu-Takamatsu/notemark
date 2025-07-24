package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist

import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi

sealed interface NoteListAction {
    data class OnLongTapNote(val noteMark: NoteMarkUi) : NoteListAction
    data class OnClickNote(val noteMark: NoteMarkUi) : NoteListAction
    data object OnCreateNoteAndNavigate : NoteListAction
    data object OnCancelDelete : NoteListAction
    data object OnDeleteNote : NoteListAction
    data object OnClickSettings: NoteListAction
}