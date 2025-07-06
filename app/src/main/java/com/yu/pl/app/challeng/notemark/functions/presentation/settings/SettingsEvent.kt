package com.yu.pl.app.challeng.notemark.functions.presentation.settings

sealed interface SettingsEvent {
    data object Error: SettingsEvent
    data object NavigateLogin: SettingsEvent
}