package com.yu.pl.app.challeng.notemark.features.presentation.note.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.models.SyncIntervalUI

@Composable
fun SettingMenu(
    modifier: Modifier = Modifier,
    layoutType: LayoutType,
    syncIntervalUI: SyncIntervalUI,
    lastSyncDateText: String,
    onClickLogout: () -> Unit,
    onClickSync: () -> Unit,
    onSelectSyncInterval: (SyncIntervalUI) -> Unit,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = when (layoutType) {
                    LayoutType.PORTRAIT -> 16.dp
                    LayoutType.LANDSCAPE -> 16.dp
                    LayoutType.TABLET -> 24.dp
                }
            )
    ) {
        DropDownSettingMenu<SyncIntervalUI>(
            modifier = Modifier.fillMaxWidth(),
            icon = ImageVector.vectorResource(R.drawable.icon_clock),
            label = "Sync interval",
            onSelectMenu = onSelectSyncInterval,
            currentSetting = MenuItem(syncIntervalUI.uiText.value(), syncIntervalUI),
            dropDownMenuList = SyncIntervalUI.entries.map { interval ->
                MenuItem(interval.uiText.value(), interval)
            },
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surface
        )
        SettingMenu(
            modifier = Modifier.fillMaxWidth(),
            icon = ImageVector.vectorResource(R.drawable.icon_refresh),
            label = "Sync Data",
            description = lastSyncDateText,
            onClick = onClickSync,
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surface
        )
        SettingMenu(
            modifier = Modifier.fillMaxWidth(),
            icon = ImageVector.vectorResource(R.drawable.icon_log_out),
            label = "Log out",
            onClick = onClickLogout,
            tintColor = MaterialTheme.colorScheme.error,
        )
    }
}