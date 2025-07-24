package com.yu.pl.app.challeng.notemark.features.presentation.note.settings

import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.util.UiText
import com.yu.pl.app.challeng.notemark.features.presentation.models.SyncIntervalUI

data class SettingsState(
    val isLoading: Boolean = false,
    val syncInterval: SyncIntervalUI = SyncIntervalUI.ManualOnly,
    val lastSyncDateStr: UiText = UiText.ResourceString(R.string.last_sync_date_never),
    val hasNetworkConnection: Boolean = true,
    val isShowConfirmSyncInLogoutDialog: Boolean = false,
    val isShowSyncFailDialog: Boolean = false
)
