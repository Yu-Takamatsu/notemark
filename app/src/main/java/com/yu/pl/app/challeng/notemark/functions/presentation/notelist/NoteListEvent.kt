package com.yu.pl.app.challeng.notemark.functions.presentation.notelist

import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi

sealed interface NoteListEvent {
    data class NavigateToEditScreen(val note: NoteMarkUi, val editMode: NoteEditMode): NoteListEvent
}