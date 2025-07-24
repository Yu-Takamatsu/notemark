package com.yu.pl.app.challeng.notemark.features.presentation.note.settings

import com.yu.pl.app.challeng.notemark.features.presentation.models.SyncIntervalUI

sealed interface SettingsAction {
    data object OnLogout: SettingsAction
    data object OnSyncData: SettingsAction
    data object OnSyncLogout: SettingsAction
    data object OnUnSyncLogout: SettingsAction
    data class OnChangeSyncInterval(val interval: SyncIntervalUI): SettingsAction
    data object OnBackNavigation: SettingsAction
    data object OnCancelSync: SettingsAction
}