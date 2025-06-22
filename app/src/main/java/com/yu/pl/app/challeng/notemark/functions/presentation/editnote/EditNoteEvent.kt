package com.yu.pl.app.challeng.notemark.functions.presentation.editnote

sealed interface EditNoteEvent {
    data object NavigateBack: EditNoteEvent
}