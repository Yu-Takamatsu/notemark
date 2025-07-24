package com.yu.pl.app.challeng.notemark.core.ui.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetStatusBarIconsColor(darkIcons: Boolean) {
    val view = LocalView.current

    val insetsController = remember(view) {
        (view.context as? Activity)
            ?.window
            ?.let { WindowCompat.getInsetsController(it, it.decorView) }
    }

    LaunchedEffect(true) {
        insetsController?.isAppearanceLightStatusBars = darkIcons
    }
}