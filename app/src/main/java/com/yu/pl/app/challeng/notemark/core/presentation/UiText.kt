package com.yu.pl.app.challeng.notemark.core.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class PlainString(val value:String):UiText
    class ResourceString(@StringRes val id:Int, vararg val args:Any):UiText

    @Composable
    fun value():String{
        return when(this){
            is PlainString -> value
            is ResourceString -> stringResource(id, *args)
        }
    }

    fun value(context: Context):String{
        return when(this){
            is PlainString -> value
            is ResourceString -> context.getString(id, *args)
        }
    }

}