package com.yu.pl.app.challeng.notemark.functions.presentation.notelist

import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi

data class NoteListState(
    val userName: String = "",
    val noteMarks: List<NoteMarkUi> = emptyList(),
    val isLoading: Boolean = false,
    val isShowDeleteConfirm: Boolean = false,
    val deleteNote:NoteMarkUi? = null
)