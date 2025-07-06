package com.yu.pl.app.challeng.notemark.functions.presentation.settings

sealed interface SettingsAction {
    data object OnLogout: SettingsAction
    data object OnBackNavigation: SettingsAction
}