package com.yu.pl.app.challeng.notemark.features.presentation.models

import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.model.SyncInterval
import com.yu.pl.app.challeng.notemark.core.ui.util.UiText

enum class SyncIntervalUI(val uiText: UiText) {
    ManualOnly(UiText.ResourceString(R.string.sync_interval_manual)),
    Minutes15(UiText.ResourceString(R.string.sync_interval_15_minutes)),
    Minutes30(UiText.ResourceString(R.string.sync_interval_30_minutes)),
    Hour1(UiText.ResourceString(R.string.sync_interval_1_hour)),
}

fun SyncInterval.toSyncIntervalUi(): SyncIntervalUI{
    return when(this){
        SyncInterval.Manual -> SyncIntervalUI.ManualOnly
        is SyncInterval.Minutes15 -> SyncIntervalUI.Minutes15
        is SyncInterval.Minutes30 -> SyncIntervalUI.Minutes30
        is SyncInterval.Hour1 -> SyncIntervalUI.Hour1
    }
}

fun SyncIntervalUI.toSyncInterval(): SyncInterval{
    return when(this){
        SyncIntervalUI.ManualOnly -> SyncInterval.Manual
        SyncIntervalUI.Minutes15 -> SyncInterval.Minutes15()
        SyncIntervalUI.Minutes30 -> SyncInterval.Minutes30()
        SyncIntervalUI.Hour1 -> SyncInterval.Hour1()
    }
}