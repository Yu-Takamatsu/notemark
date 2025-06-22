package com.yu.pl.app.challeng.notemark.core.presentation.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isUseTable(): Boolean {
    val configuration = LocalConfiguration.current
    return when (configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
        Configuration.SCREENLAYOUT_SIZE_XLARGE,
        Configuration.SCREENLAYOUT_SIZE_LARGE,
            -> true

        Configuration.SCREENLAYOUT_SIZE_NORMAL,
        Configuration.SCREENLAYOUT_SIZE_SMALL,
            -> false

        else -> false
    }
}