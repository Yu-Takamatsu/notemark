package com.yu.pl.app.challeng.notemark.core.ui.util

import androidx.compose.runtime.Composable
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.core.ui.util.model.Orientation

@Composable
fun getLayoutType(): LayoutType {

    return when(getOrientation()){
        Orientation.LANDSCAPE -> LayoutType.LANDSCAPE
        Orientation.PORTRAIT -> {
            if(isUseTable()) {
                LayoutType.TABLET
            }else {
                LayoutType.PORTRAIT
            }
        }
    }
}