package com.yu.pl.app.challeng.notemark.core.presentation.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun RequestScreenOrientation(
    orientation: ScreenOrientation,
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {

        val activity = context.findActivity()

        val originalOrientation = activity?.requestedOrientation
        activity?.requestedOrientation = when (orientation) {
            ScreenOrientation.Portrait -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            ScreenOrientation.Landscape -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            ScreenOrientation.None -> ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }

        onDispose {
            originalOrientation?.let {
                activity.requestedOrientation = it
            }
        }
    }
}

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context // Activity のインスタンスが見つかったら返す
        context = context.baseContext // 次のContextWrapper の基本コンテキストへ移動
    }
    return null // Activity が見つからなかった場合
}

enum class ScreenOrientation() {
    Portrait,
    Landscape,
    None
}