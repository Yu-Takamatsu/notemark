package com.yu.pl.app.challeng.notemark.functions.presentation.registration

import com.yu.pl.app.challeng.notemark.core.presentation.util.UiText

sealed interface RegistrationEvent {
    data class ShowSnackBar(val message: UiText): RegistrationEvent
    data object SuccessRegistration: RegistrationEvent
}