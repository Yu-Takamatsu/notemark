package com.yu.pl.app.challeng.notemark.features.presentation.note.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.note.settings.components.SettingMenu
import com.yu.pl.app.challeng.notemark.features.presentation.note.settings.components.SettingTopBar

@Composable
fun SettingsScreen(
    state: SettingsState,
    action: (SettingsAction) -> Unit,
    layoutType: LayoutType,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        val baseModifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(innerPadding)
            .let {
                if (layoutType == LayoutType.LANDSCAPE) {
                    it.windowInsetsPadding(WindowInsets.displayCutout)
                } else {
                    it
                }
            }

        Column(
            modifier = baseModifier
        ) {
            SettingTopBar(
                modifier = Modifier.fillMaxWidth(),
                layoutType = layoutType,
                onClickBack = {
                    action(SettingsAction.OnBackNavigation)
                }
            )
            SettingMenu(
                modifier = Modifier.fillMaxSize(),
                layoutType = layoutType,
                onClickLogout = {
                    action(SettingsAction.OnLogout)
                },
                onClickSync = {
                    action(SettingsAction.OnSyncData)
                },
                syncIntervalUI = state.syncInterval,
                lastSyncDateText = state.lastSyncDateStr.value(),
                onSelectSyncInterval = { interval ->
                    action(SettingsAction.OnChangeSyncInterval(interval))
                },
            )
        }
    }
}


