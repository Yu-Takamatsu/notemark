package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist

import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi

sealed interface NoteListEvent {
    data class NavigateToEditScreen(val note: NoteMarkUi, val editMode: NoteEditMode): NoteListEvent
}