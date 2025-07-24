package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist

import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi

data class NoteListState(
    val userName: String = "",
    val noteMarks: List<NoteMarkUi> = emptyList(),
    val isLoading: Boolean = false,
    val isShowDeleteConfirm: Boolean = false,
    val deleteNote:NoteMarkUi? = null,
    val isOnline: Boolean = true
)