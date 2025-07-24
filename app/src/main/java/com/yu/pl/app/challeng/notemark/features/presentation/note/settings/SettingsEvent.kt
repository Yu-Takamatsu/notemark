package com.yu.pl.app.challeng.notemark.features.presentation.note.settings

import com.yu.pl.app.challeng.notemark.core.ui.util.UiText

sealed interface SettingsEvent {
    data object NavigateLogin: SettingsEvent
    data class ShowToastMessage(val text: UiText): SettingsEvent
}