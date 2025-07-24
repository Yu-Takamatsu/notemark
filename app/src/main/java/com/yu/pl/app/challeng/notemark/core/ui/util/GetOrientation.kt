package com.yu.pl.app.challeng.notemark.core.ui.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.yu.pl.app.challeng.notemark.core.ui.util.model.Orientation

@Composable
fun getOrientation(): Orientation {
    val configuration = LocalConfiguration.current
    return when(configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE ->{
            Orientation.LANDSCAPE
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            Orientation.PORTRAIT
        }
        else -> {
            Orientation.PORTRAIT
        }
    }
}