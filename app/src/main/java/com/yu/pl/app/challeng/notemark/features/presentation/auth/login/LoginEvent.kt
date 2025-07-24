package com.yu.pl.app.challeng.notemark.features.presentation.auth.login

import com.yu.pl.app.challeng.notemark.core.ui.util.UiText

sealed interface LoginEvent {
    data object SuccessLogin: LoginEvent
    data class ShowToastMessage(val message:UiText): LoginEvent
}