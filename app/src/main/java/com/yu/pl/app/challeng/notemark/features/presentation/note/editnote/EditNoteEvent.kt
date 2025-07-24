package com.yu.pl.app.challeng.notemark.features.presentation.note.editnote

sealed interface EditNoteEvent {
    data object NavigateBack: EditNoteEvent
}