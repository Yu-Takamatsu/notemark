package com.yu.pl.app.challeng.notemark.navigation

import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {
    @Serializable
    data object Landing: Destination
    @Serializable
    data object Registration: Destination
    @Serializable
    data object Login: Destination
    @Serializable
    data object Dummy: Destination

    @Serializable
    data object NoteList: Destination
    @Serializable
    data class EditNote(val note: NoteMarkUi, val initialEditMode: NoteEditMode): Destination

    @Serializable
    data object Settings: Destination
}