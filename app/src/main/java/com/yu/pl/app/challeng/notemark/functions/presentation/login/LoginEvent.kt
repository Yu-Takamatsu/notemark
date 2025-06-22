package com.yu.pl.app.challeng.notemark.functions.presentation.login

import com.yu.pl.app.challeng.notemark.core.presentation.util.UiText

sealed interface LoginEvent {
    data object SuccessLogin: LoginEvent
    data class ShowSnackBar(val message:UiText): LoginEvent
}